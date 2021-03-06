/**
 * Copyright (c) 2012-2018 http://www.eryansky.com
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.eryansky.modules.sys.service;

import com.eryansky.common.orm.Page;
import com.eryansky.common.orm.model.Parameter;
import com.eryansky.common.orm.mybatis.interceptor.BaseInterceptor;
import com.eryansky.core.orm.mybatis.service.CrudService;
import com.eryansky.modules.sys._enum.VersionLogType;
import com.eryansky.modules.sys._enum.YesOrNo;
import com.eryansky.modules.sys.dao.VersionLogDao;
import com.eryansky.modules.sys.mapper.VersionLog;
import com.eryansky.utils.AppConstants;
import org.springframework.stereotype.Service;

/**
 * 版本更新日志
 * @author 尔演&Eryan eryanwcp@gmail.com
 * @date 2015-09-29
 */
@Service
public class VersionLogService extends CrudService<VersionLogDao,VersionLog>{

    /**
     * 删除
     * @param id
     */
    public void delete(String id) {
        dao.delete(new VersionLog(id));
    }

    public Page<VersionLog> findPage(Page<VersionLog> page, Parameter parameter) {
        parameter.put(BaseInterceptor.PAGE,page);
        parameter.put(VersionLog.FIELD_STATUS,VersionLog.STATUS_NORMAL);
        parameter.put(BaseInterceptor.DB_NAME, AppConstants.getJdbcType());
        page.setResult(dao.findQueryList(parameter));
        return page;
    }

    /**
     * 清空所有更新日志数据
     */
    public void removeAll(){
        int reslutCount = dao.removeAll();
        logger.debug("清空版本更新日志：{}",reslutCount);
    }
    /**
     * 根据版本号查找
     * @param versionLogType {@link VersionLogType}
     * @param versionCode
     * @return
     */
    public VersionLog getByVersionCode(String versionLogType,String versionCode) {
        VersionLog versionLog = new VersionLog();
        versionLog.setVersionLogType(versionLogType);
        versionLog.setVersionCode(versionCode);
        return dao.getByVersionCode(versionLog);
    }

    /**
     * 获取当前最新版本
     * @param versionLogType
     * @return
     */
    public VersionLog getLatestVersionLog(String versionLogType) {
        return getLatestVersionLog(versionLogType, YesOrNo.YES.getValue());
    }

    /**
     * 获取当前最新版本
     * @param versionLogType
     * @param isPub
     * @return
     */
    public VersionLog getLatestVersionLog(String versionLogType,String isPub) {
        VersionLog entity = new VersionLog();
        entity.setVersionLogType(versionLogType);
        entity.setIsPub(isPub);
        return dao.getLatestVersionLog(entity);
    }
}

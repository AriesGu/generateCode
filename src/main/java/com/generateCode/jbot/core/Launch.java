package com.generateCode.jbot.core;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.generateCode.jbot.web.entity.DbInfo;
import com.generateCode.jbot.common.model.RequestModel;
import com.generateCode.jbot.core.after.AfterHandle;
import com.generateCode.jbot.core.before.BeforeHandle;
import com.generateCode.jbot.core.work.common.CommonHandle;
import com.generateCode.jbot.core.work.doc.DocHandle;
import com.generateCode.jbot.core.work.java.*;
import com.generateCode.jbot.core.work.resources.MapperHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 把model工程按照需求进行修改
 *
 * @author generateCode
 */
public class Launch {

    private static Logger logger = LoggerFactory.getLogger(Launch.class);
    private static List<Future<Boolean>> futures = new ArrayList<>();
    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
    private static ExecutorService executorService = new ThreadPoolExecutor(
            5,
            50,
            5000L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1024),
            namedThreadFactory,
            new ThreadPoolExecutor.AbortPolicy());

    public static boolean start(String projectName, String packageName, String authorName, DbInfo dbInfo) {
        logger.info("jbot core,Launch,start-------->dbInfo:{},projectName:{},packageName:{}", dbInfo, projectName, packageName);
        try {
            //1、准备工作
            RequestModel model = BeforeHandle.start(projectName, packageName,authorName, dbInfo);
            //2、通用文件修改
            futures.add(executorService.submit(() -> {
                CommonHandle.start(model);
                return true;
            }));
            //3、java文件修改
            futures.add(executorService.submit(() -> {
                EntityHandle.start(model);
                DaoHandle.start(model);
                ServiceHandle.start(model);
                ServiceImplHandle.start(model);
                RestHandle.start(model);
                return true;
            }));
            //4、mapper文件修改
            futures.add(executorService.submit(() -> {
                MapperHandle.start(model);
                return true;
            }));
            //5、doc文件修改
            futures.add(executorService.submit(() -> {
                DocHandle.start(model);
                return true;
            }));
            for (Future<Boolean> future : futures) {
                if (!future.isDone()) {
                    future.get();
                }
            }
            //5、善后工作
            AfterHandle.start(model);
            return true;
        } catch (Exception e) {
            logger.error("core start exception", e);
        }
        return false;
    }
}

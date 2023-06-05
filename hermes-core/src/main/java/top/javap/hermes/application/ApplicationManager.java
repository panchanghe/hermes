package top.javap.hermes.application;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/1
 **/
public class ApplicationManager {

    private static final ConcurrentMap<String, Application> applications = new ConcurrentHashMap<>();

    public static void add() {
        
    }
}
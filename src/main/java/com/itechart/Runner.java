package com.itechart;

import com.itechart.core.BandwidthManager;
import com.itechart.core.stream.InputStreamWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class Runner {
    private class Task implements Runnable {
        private final Logger LOGGER = LoggerFactory.getLogger(Task.class);
        private final InputStreamWrapper inputStreamWrapper;

        public Task(final InputStreamWrapper inputStreamWrapper) {
            this.inputStreamWrapper = inputStreamWrapper;
        }

        @Override
        public void run() {
            try {
                while ((inputStreamWrapper.read()) != -1) {
                    LOGGER.info(inputStreamWrapper.toString());
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            } finally {
                if (inputStreamWrapper != null) {
                    try {
                        inputStreamWrapper.close();
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }
            }
        }
    }

    private void go() {
        BandwidthManager bandwidthManager = BandwidthManager.getInstance();
        bandwidthManager.init("12:00am-02:23pm=80|02:23pm-11:00pm=100|11:00pm-12:00am=40");

        InputStreamWrapper wrapper1 = new InputStreamWrapper(this.getClass().getResourceAsStream("/test.txt"));
        InputStreamWrapper wrapper2 = new InputStreamWrapper(this.getClass().getResourceAsStream("/test.txt"));
        InputStreamWrapper wrapper3 = new InputStreamWrapper(this.getClass().getResourceAsStream("/test.txt"));
        InputStreamWrapper wrapper4 = new InputStreamWrapper(this.getClass().getResourceAsStream("/test.txt"));

        new Thread(new Task(wrapper1)).start();
        new Thread(new Task(wrapper2)).start();
        new Thread(new Task(wrapper3)).start();
        new Thread(new Task(wrapper4)).start();
    }

    public static void main(String[] args) {
        new Runner().go();
    }
}
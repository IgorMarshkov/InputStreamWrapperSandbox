package com.itechart;

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
        InputStream inputStream = this.getClass().getResourceAsStream("/test.txt");
        String bandwidthPeriods = "12:00am-02:23pm=100|02:23pm-11:00pm=200|11:00pm-12:00am=";

        InputStreamWrapper wrapper1 = new InputStreamWrapper(inputStream);
        wrapper1.initBandwidthPeriods(bandwidthPeriods);

        InputStreamWrapper wrapper2 = new InputStreamWrapper(inputStream);
        wrapper2.initBandwidthPeriods(bandwidthPeriods);

        InputStreamWrapper wrapper3 = new InputStreamWrapper(inputStream);
        wrapper3.initBandwidthPeriods(bandwidthPeriods);

        InputStreamWrapper wrapper4 = new InputStreamWrapper(inputStream);
        wrapper4.initBandwidthPeriods(bandwidthPeriods);

        new Thread(new Task(wrapper1)).start();
        new Thread(new Task(wrapper2)).start();
        new Thread(new Task(wrapper3)).start();
        new Thread(new Task(wrapper4)).start();
    }

    public static void main(String[] args) {
        new Runner().go();
    }
}
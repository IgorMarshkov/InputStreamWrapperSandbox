package com.itechart;

import com.itechart.core.stream.InputStreamWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class Runner {
    private class Task implements Runnable {
        private final Logger LOGGER = LoggerFactory.getLogger(Task.class);
        private final InputStreamWrapper inputStream;

        public Task(final InputStreamWrapper inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            try {
                while ((inputStream.read()) != -1) {
                    LOGGER.info(inputStream.toString());
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }
            }
        }
    }

    private void go() {
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
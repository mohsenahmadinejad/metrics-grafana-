package com.example.prometheus.controller;

import io.micrometer.core.instrument.Metrics;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping(path = "/short")
    public ResponseEntity shortResponse() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(path = "/long")
    public ResponseEntity longResponse() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping("/hello")
    public String hello(){
        Metrics.counter("hello.count").increment();
        Metrics.gauge("hello.gauge",Math.random()*100);
        Metrics.timer("hello.timer").record(this::randomDelay);
        randomDelay();
        return "Hello Demo...";
    }
    private void randomDelay(){

        int min_millisecond_delay = 50;
        int max_millisecond_delay = 250;

        int millisecond_delay = new Random().nextInt(max_millisecond_delay-min_millisecond_delay) + min_millisecond_delay;

        try {
            TimeUnit.MILLISECONDS.sleep(millisecond_delay);
        } catch (InterruptedException ignored) {}
    }

}

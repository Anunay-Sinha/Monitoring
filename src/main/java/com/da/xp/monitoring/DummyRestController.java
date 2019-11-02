package com.da.xp.monitoring;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.distribution.Histogram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/dummy")
@Timed
public class DummyRestController {
    private ArrayList arrayList;


    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String ping(@RequestParam(value = "delay", required = false, defaultValue = "0") int delay) {
        final Counter counter = Metrics.counter("ping.count", "type", "ping");
        counter.increment();
        if(delay>10) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "Pong";
    }

    @RequestMapping(value = "/ram", method = RequestMethod.GET)
    public String ram() {

        arrayList = new ArrayList();
        for(int i =0;i< 10000;i++)
            arrayList.add("TestString" + i);
        return "Loaded";
    }

    @RequestMapping(value = "/ram/clear", method = RequestMethod.GET)
    public String clearRam() {
        arrayList = null;
        return "Cleared";
    }
}

package org.mmpp.sample.pi4j;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {
    private static GpioController gpio;
    private static GpioPinDigitalInput button;
    private static void initialize() {
        gpio = GpioFactory.getInstance();
        // Raspberry pi の piは 16 : GPIO_27
        // gpio pin #02 as an input pin with its internal pull down resistor enabled
        button = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);
        // set shutdown state for this input pin
        button.setShutdownOptions(true);

        // create and register gpio pin listener
        button.addListener(new GpioPinListenerDigital(){
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event)
            {
                // display pin state on console
                System.out.println(" Switch change detected: " + event.getPin() + " = " + event.getState());
            }

        });
    }

    public static void main(String[] args) throws Exception {
        System.out.println("<--Pi4J--> GPIO Listen Example ... started.");

        initialize();
        // keep program running until user aborts (CTRL-C)
        while(true) {
            Thread.sleep(1500);
        }
    }

}

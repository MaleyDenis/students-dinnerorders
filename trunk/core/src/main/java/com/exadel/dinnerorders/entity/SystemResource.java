package com.exadel.dinnerorders.entity;

import com.exadel.dinnerorders.service.Configuration;

import java.io.IOException;

public enum SystemResource {
    HOST {
        public String toString() {
            try {
                return (Configuration.getProperty("host"));
            } catch (IOException e) {
                return "IOException ";
            }

        }
    },
    PORT {
        public String toString() {
            try {
                return (Configuration.getProperty("port"));
            } catch (IOException e) {
                return "IOException ";
            }

        }
    };


}




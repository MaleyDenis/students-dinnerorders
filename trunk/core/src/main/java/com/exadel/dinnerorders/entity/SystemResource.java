package com.exadel.dinnerorders.entity;

import com.exadel.dinnerorders.service.Configuration;

public enum SystemResource {
    HOST {
        public String toString() {

                return (Configuration.getProperty("host"));

        }
    },
    PORT {
        public String toString() {

                return (Configuration.getProperty("port"));

        }
    };


}




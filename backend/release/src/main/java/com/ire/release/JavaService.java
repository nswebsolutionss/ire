package com.ire.release;

import java.util.Objects;

public class JavaService implements Service{

    private final Service delegate;

    public JavaService(Service delegate) {

        this.delegate = delegate;
    }

    @Override
    public void deploy() {
        delegate.deploy();
    }


    public enum Task {
        DEPLOY("DEPLOY");

        private final String deploy;

        Task(String deploy) {

            this.deploy = deploy;
        }

        public String representation() {
            return deploy;
        }

        public Task parse(String name) {
            if(Objects.equals(name, "DEPLOY")) {
                return DEPLOY;
            }
            return null;
        }
    }
}


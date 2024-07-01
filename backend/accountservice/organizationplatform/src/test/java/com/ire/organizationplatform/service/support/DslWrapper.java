package com.ire.organizationplatform.service.support;

public class DslWrapper<T> {
    private final T dsl;

    public DslWrapper(final T dsl) {
        this.dsl = dsl;
    }

    public T when() {
        return this.dsl;
    }

    public T then() {
        return this.dsl;
    }

    public T given() {
        return this.dsl;
    }

    public T and() {
        return this.dsl;
    }
}

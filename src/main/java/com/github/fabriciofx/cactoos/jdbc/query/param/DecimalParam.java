/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2018 Fabrício Barros Cabral
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.fabriciofx.cactoos.jdbc.query.param;

import com.github.fabriciofx.cactoos.jdbc.QueryParam;
import java.math.BigDecimal;
import java.sql.PreparedStatement;

/**
 * Decimal value.
 *
 * @since 0.1
 */
public final class DecimalParam implements QueryParam {
    /**
     * Name.
     */
    private final String name;

    /**
     * Value.
     */
    private final BigDecimal value;

    /**
     * Ctor.
     */
    public DecimalParam() {
        this("unknown", BigDecimal.ZERO);
    }

    /**
     * Ctor.
     * @param name The name
     * @param value The value
     */
    public DecimalParam(final String name, final String value) {
        this(name, new BigDecimal(value));
    }

    /**
     * Ctor.
     * @param name The name
     * @param value The value
     */
    public DecimalParam(final String name, final BigDecimal value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public void prepare(
        final PreparedStatement stmt,
        final int index
    ) throws Exception {
        stmt.setBigDecimal(index, this.value);
    }

    @Override
    public String asString() throws Exception {
        return this.value.toString();
    }
}

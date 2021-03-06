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
import java.nio.ByteBuffer;
import java.sql.PreparedStatement;
import java.util.UUID;

/**
 * UUID param.
 *
 * @since 0.2
 */
public final class UuidAsBytesParam implements QueryParam {
    /**
     * Name.
     */
    private final String id;

    /**
     * Value.
     */
    private final UUID value;

    /**
     * Ctor.
     * @param name The id
     * @param value The apply
     */
    public UuidAsBytesParam(final String name, final UUID value) {
        this.id = name;
        this.value = value;
    }

    @Override
    public String name() {
        return this.id;
    }

    @Override
    public void prepare(
        final PreparedStatement stmt,
        final int index
    ) throws Exception {
        // @checkstyle MagicNumber (1 line)
        final ByteBuffer bbuf = ByteBuffer.wrap(new byte[16]);
        bbuf.putLong(this.value.getMostSignificantBits());
        bbuf.putLong(this.value.getLeastSignificantBits());
        stmt.setBytes(index, bbuf.array());
    }

    @Override
    public String asString() throws Exception {
        return this.value.toString();
    }
}

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
package com.github.fabriciofx.cactoos.jdbc;

import java.util.UUID;
import org.cactoos.Scalar;
import org.cactoos.Text;

/**
 * Universally Unique IDentifier (UUID).
 *
 * @since 0.2
 */
public final class Uuid implements Scalar<UUID>, Text {
    /**
     * UUID val.
     */
    private final UUID val;

    /**
     * Ctor.
     */
    public Uuid() {
        this(UUID.randomUUID());
    }

    /**
     * Ctor.
     * @param uuid UUID.
     */
    public Uuid(final UUID uuid) {
        this.val = uuid;
    }

    @Override
    public UUID value() throws Exception {
        return this.val;
    }

    @Override
    public String asString() throws Exception {
        return this.val.toString();
    }

    /**
     * Small UUID string.
     * @return A small string ID
     * @throws Exception if fails
     */
    public String asSmallString() throws Exception {
        // @checkstyle MagicNumberCheck (1 line)
        return this.asString().substring(0, 5);
    }
}

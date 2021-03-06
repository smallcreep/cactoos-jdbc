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
package com.github.fabriciofx.cactoos.jdbc.server;

import com.github.fabriciofx.cactoos.jdbc.RandomDatabaseName;
import com.github.fabriciofx.cactoos.jdbc.Server;
import com.github.fabriciofx.cactoos.jdbc.Session;
import com.github.fabriciofx.cactoos.jdbc.SqlScript;
import com.github.fabriciofx.cactoos.jdbc.query.SimpleQuery;
import com.github.fabriciofx.cactoos.jdbc.session.AuthSession;
import com.github.fabriciofx.cactoos.jdbc.source.PsqlSource;
import com.github.fabriciofx.cactoos.jdbc.stmt.Update;
import java.io.IOException;
import org.cactoos.scalar.StickyScalar;
import org.cactoos.scalar.UncheckedScalar;
import org.cactoos.text.FormattedText;
import org.cactoos.text.JoinedText;

/**
 * PostgreSQL server, for unit testing.
 *
 * @since 0.2
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings(
    {
        "PMD.AvoidDuplicateLiterals",
        "PMD.AvoidCatchingGenericException"
    }
)
public final class PsqlServer implements Server {
    /**
     * Database name.
     */
    private final UncheckedScalar<String> dbname;
    /**
     * Hostname.
     */
    private final String host;
    /**
     * Port.
     */
    private final int port;
    /**
     * Username.
     */
    private final String username;
    /**
     * User password.
     */
    private final String password;
    /**
     * SQL Script to initialize the database.
     */
    private final SqlScript script;

    /**
     * Ctor.
     */
    public PsqlServer() {
        this(SqlScript.NOP);
    }

    /**
     * Ctor.
     * @param scrpt SQL Script to initialize the database
     */
    public PsqlServer(final SqlScript scrpt) {
        // @checkstyle MagicNumber (1 line)
        this("localhost", 5432, "postgres", "postgres", scrpt);
    }

    /**
     * Ctor.
     * @param hst Hostname
     * @param prt Port
     * @param srnm Username
     * @param psswrd User password
     * @param scrpt SQL Script to initialize the database
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public PsqlServer(
        final String hst,
        final int prt,
        final String srnm,
        final String psswrd,
        final SqlScript scrpt
    ) {
        this.dbname = new UncheckedScalar<>(
            new StickyScalar<>(
                () -> new RandomDatabaseName().asString()
            )
        );
        this.host = hst;
        this.port = prt;
        this.username = srnm;
        this.password = psswrd;
        this.script = scrpt;
    }

    @Override
    public void start() throws Exception {
        new Update(
            new AuthSession(
                new PsqlSource(
                    this.host,
                    this.port,
                    ""
                ),
                this.username,
                this.password
            ),
            new SimpleQuery(
                new FormattedText(
                    new JoinedText(
                        " ",
                        "CREATE DATABASE %s WITH OWNER %s",
                        "ENCODING utf8 TEMPLATE template1;"
                    ),
                    this.dbname.value(),
                    this.username
                )
            )
        ).result();
        this.script.exec(this.session());
    }

    @Override
    public void stop() throws Exception {
        new Update(
            new AuthSession(
                new PsqlSource(
                    this.host,
                    this.port,
                    ""
                ),
                this.username,
                this.password
            ),
            new SimpleQuery(
                new FormattedText(
                    "DROP DATABASE IF EXISTS %s",
                    this.dbname.value()
                )
            )
        ).result();
    }

    @Override
    public Session session() {
        return new AuthSession(
            new PsqlSource(
                this.host,
                this.port,
                this.dbname.value()
            ),
            this.username,
            this.password
        );
    }

    @Override
    public void close() throws IOException {
        try {
            this.stop();
            // @checkstyle IllegalCatchCheck (1 line)
        } catch (final Exception ex) {
            throw new IOException(ex);
        }
    }
}

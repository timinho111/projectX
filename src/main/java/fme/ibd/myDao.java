package fme.ibd;

import org.sql2o.*;

public class myDao {

    private Sql2o sql2o;

    public myDao() {
        this.sql2o = new Sql2o("jdbc:exa://exasol-p-n11.hg.fresenius.de:8563", "FME_AKQUISE", "FME_AKQUISE2018");
    }
}
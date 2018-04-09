package fme.ibd;

import org.sql2o.*;

import java.awt.*;
import java.util.List;

public class DBSettings {
    Sql2o sql2o;

    public List<Verbund> getAllVerbund(){
        String sql =
                "SELECT Verbund " +
                        "FROM Basis_Kunden";

        try(Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Verbund.class);
        }
    }
}

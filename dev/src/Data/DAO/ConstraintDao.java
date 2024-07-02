package dev.src.Data.DAO;

import dev.src.Domain.Constraint;
import dev.src.repostory.Constraintrep;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static java.lang.System.load;

public class ConstraintDAO {
    DBConnection DB;
    Constraintrep CR;

    public ConstraintDAO(DBConnection DB, Constraintrep CR) {
        this.DB=DB;
        this.CR=CR;
    }

    @Override
    public void put(Constraint constraint) {
        PreparedStatement ps=null;
        String sql="INSERT INTO Constraint (empID,ConstraintDate,ConstraintShiftType) VALUES(?,?,?)";
        try {
            ps=DB.getConnection().prepareStatement(sql);
            ps.setString(1,constraint.getEmp().getID());
            ps.setString(2, String.valueOf(Date.valueOf(constraint.getShiftDate())));
            ps.setString(3, String.valueOf(constraint.getShiftType()));
        } catch (SQLException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Constraint select(LocalDate date) {
        String sql = "SELECT * FROM Constraint WHERE date=?";
        Constraint constraint = CR.getConstraint(date);
        if (constraint != null) {
            return constraint;
        }
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = DB.getConnection().prepareStatement(sql);
            ps.setDate(1, Date.valueOf(date));
            rs = ps.executeQuery();
            rs.next();
            constraint = load(rs);
            CR.addConstraint(date, constraint);
            return constraint;
        } catch (SQLException e) {
            throw new IllegalArgumentException();
        }
    }
}

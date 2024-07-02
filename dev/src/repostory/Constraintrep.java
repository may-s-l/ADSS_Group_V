package dev.src.repostory;

import dev.src.Domain.Constraint;
import dev.src.Domain.MyMap;

import java.time.LocalDate;
import java.util.Set;

public class Constraintrep {
    private MyMap<LocalDate, Constraint> constraintrep;

    public Constraintrep() {
        constraintrep=new MyMap<>();
    }

    public void addConstraint(LocalDate date,Constraint constraint) {
        constraintrep.put(date,constraint);
    }

    public Constraint getConstraint(LocalDate date) {
        return constraintrep.get(date);
    }

    public boolean isConstraintExist(LocalDate date) {
        return constraintrep.containsKey(date);
    }

    public Set<LocalDate> getAllConstraintDate() {
        return constraintrep.getKeys();
    }

    public void removeConstraint(LocalDate date) {
        constraintrep.remove(date);
    }

    public int getConstraintCount() {
        return constraintrep.size();
    }

}

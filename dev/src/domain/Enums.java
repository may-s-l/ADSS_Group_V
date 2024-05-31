package dev.src.domain;


public class Enums {
    public enum Shift_type {
        MORNING("Morning", 6, 14), EVENING("Evening", 14, 22);
        private final String name;
        private int startHour;
        private int endHour;

        Shift_type(String name, int startHour, int endHour) {
            this.name = name;
            this.startHour = startHour;
            this.endHour = endHour;
        }
        public String getName() {
            return name;
        }
        public int getStartHour() {
            return startHour;
        }
        public int getEndHour() {
            return endHour;
        }
        public void setStartHour(int startHour) {
            this.startHour = startHour;
        }
        public void setEndHour(int endHour) {
            this.endHour = endHour;
        }
        @Override
        public String toString() {
            return name + " shift: " + startHour + " - " + endHour;
        }
        }

    public enum job_type {FULL, PART}

    public enum Salary_type {GLOBAL, HOURLY}


}
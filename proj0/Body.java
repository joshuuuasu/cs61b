public class Body {
    final static double G = 6.67e-11;
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    
    public Body(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Body(Body b) {
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
    }

    public double calcDistance(Body b) {
        double distance;
        distance = Math.sqrt( Math.pow(this.xxPos - b.xxPos, 2) + Math.pow(this.yyPos - b.yyPos, 2) );
        return distance;
    }

    public double calcForceExertedBy(Body b) {
        double force;
        force = G * this.mass * b.mass / Math.pow(this.calcDistance(b), 2);
        return force;
    }

    public double calcForceExertedByX(Body b) {
        double forcex;
        forcex = this.calcForceExertedBy(b) * (b.xxPos - this.xxPos) / this.calcDistance(b);
        return forcex;
    }

    public double calcForceExertedByY(Body b) {
        double forcey;
        forcey = this.calcForceExertedBy(b) * (b.yyPos - this.yyPos) / this.calcDistance(b);
        return forcey;
    }

    public double calcNetForceExertedByX(Body[] args) {
        double forcex = 0;
        for (Body b: args) {
            if (this.equals(b)) {
                continue;
            }
            forcex += this.calcForceExertedByX(b);
        }
        return forcex;
    }

    public double calcNetForceExertedByY(Body[] args) {
        double forcey = 0;
        for (Body b: args) {
            if (this.equals(b)) {
                continue;
            }
            forcey += this.calcForceExertedByY(b);
        }
        return forcey;
    }

    public void update(double dt, double fX, double fY) {
        double accelxx = fX / this.mass;
        double accelyy = fY / this.mass;
        this.xxVel += accelxx * dt;
        this.yyVel += accelyy * dt;
        this.xxPos += this.xxVel * dt;
        this.yyPos += this.yyVel * dt;
    }
    
    public void draw() {
        StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
    }
}
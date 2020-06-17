public class NBody {
    public static double readRadius(String file_name) {
        In in = new In(file_name);
        int num_planets = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static Body[] readBodies(String file_name) {
        In in = new In(file_name);
        int num_planets = in.readInt();
        double radius = in.readDouble();
        Body[] bodies = new Body[num_planets];

        for (int i = 0; i < num_planets; i++) {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String imgFilename = in.readString();
            bodies[i] = new Body(xxPos, yyPos, xxVel, yyVel, mass, imgFilename);
        }
        return bodies;
    }

    public static void main(String [] args) {
        Double T = Double.parseDouble(args[0]);
        Double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        Double radius = readRadius(filename);
        Body[] bodies = readBodies(filename);
        
        StdDraw.enableDoubleBuffering();
        
        StdDraw.setScale(-radius, radius);

        for (Double time = 0.0; time <= T; time += dt) {
            Double[] xForces = new Double[bodies.length];
            Double[] yForces = new Double[bodies.length];
            
            for (int i = 0; i < bodies.length; i++) {
                xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
                yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
            }
            
            for (int i = 0; i < bodies.length; i++) {
                bodies[i].update(dt, xForces[i], yForces[i]);
            }
            
            StdDraw.picture(0, 0, "images/starfield.jpg");
        
            for (Body b: bodies) {
                b.draw();
            }

            StdDraw.show();
            StdDraw.pause(10);
        }

        StdOut.printf("%d\n", bodies.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < bodies.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);   
        }
    }
}
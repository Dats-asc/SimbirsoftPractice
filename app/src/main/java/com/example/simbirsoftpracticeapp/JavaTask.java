package com.example.simbirsoftpracticeapp;

import java.util.List;
import java.util.Map;

public class JavaTask {

    Runnable myClosure = () -> System.out.println("I love Java");

    public void repeatTask(int times, Runnable task) {
        for (int i = 0; i < times; i++) {
            task.run();
        }
    }

    public void javaTaskStart() {
        myClosure.run();

        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println("I love Java");
            }
        };

        repeatTask(10, task);
    }
}

class Sheet {

    Point postion;

    public Sheet(Point postion) {
        this.postion = postion;
    }

    public Point moveTo(Point point, Direction direction) {
        switch (direction) {
            case Up: {
                point.y += 1;
                point.print();
                return point;
            }
            case Down: {
                point.y -= 1;
                point.print();
                return point;
            }
            case Right: {
                point.x += 1;
                point.print();
                return point;
            }
            case Left: {
                point.x -= 1;
                point.print();
                return point;
            }
            default: return point;
        }
    }

    public Point moveWithPath(List<Direction> path){
        Point location = new Point(0, 0);
        for (Direction direction: path){
            moveTo(location, direction);
        }
        return location;
    }
}

class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void print(){
        System.out.println(String.format("x: %s, y: %s", x, y));
    }
}

enum Direction {
    Up, Down, Right, Left
}

interface Shape{

    double perimeter();

    double area();
}

class Rectangle implements Shape{

    private double width;
    private double height;

    public Rectangle(double width, double height){
        this.width = width;
        this.height = height;
    }

    @Override
    public double perimeter() {
        return width * 2 + height * 2;
    }

    @Override
    public double area() {
        return width * height;
    }
}

class Square implements Shape{

    private double side;

    public Square(double side){
        this.side = side;
    }

    @Override
    public double perimeter() {
        return side * 4;
    }

    @Override
    public double area() {
        return side * side;
    }
}

class Circle implements Shape{

    private double diameter;

    public Circle(double diameter){
        this.diameter = diameter;
    }

    @Override
    public double perimeter() {
        return diameter * 3.14;
    }

    @Override
    public double area() {
        return 3.14 * diameter * diameter;
    }
}
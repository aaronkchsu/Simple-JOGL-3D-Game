package cs2420;

import assignment11.KDOigleTree;
import assignment11.Oigle;

import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;
import cs2420.GameTest.Sphere;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.ListIterator;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.swing.JApplet;

public class GameTest
  extends JApplet
  implements GLEventListener, MouseListener, MouseMotionListener, KeyListener
{
  double sqr(double x)
  {
    return x * x;
  }
  
  public class Sphere
  {
    double x;
    double y;
    double z;
    double vx;
    double vy;
    double vz;
    double r;
    double g;
    double b;
    double radius;
    long lastTime;
    boolean bomb;
    long totalTime;
    double initialRadius;
    
    Sphere(double x, double y, double z, double vx, double vy, double vz, double r, double g, double b, double radius, boolean bomb)
    {
      this.x = x;
      this.y = y;
      this.z = z;
      this.vx = vx;
      this.vy = vy;
      this.vz = vz;
      this.r = r;
      this.g = g;
      this.b = b;
      this.radius = radius;
      this.bomb = bomb;
      this.initialRadius = radius;
      this.lastTime = System.currentTimeMillis();
    }
    
    public void draw(GLAutoDrawable gld, GLUT gt)
    {
      GL2 gl = gld.getGL().getGL2();
      float[] color = { (float)this.r, (float)this.g, (float)this.b, 1.0F };
      gl.glMaterialfv(1032, 5634, color, 0);
      gl.glPushMatrix();
      gl.glTranslated(this.x, this.y, this.z);
      gt.glutSolidSphere(this.radius, 20, 20);
      gl.glPopMatrix();
    }
    
    public void update()
    {
      long dt = System.currentTimeMillis() - this.lastTime;
      this.lastTime = System.currentTimeMillis();
      if (this.bomb) {
        this.vy -= dt * 9.8E-005D;
      }
      this.x += this.vx * dt;
      this.y += this.vy * dt;
      this.z += this.vz * dt;
      if (this.x < -100.0D + this.radius)
      {
        this.x = (-100.0D + this.radius);
        this.vx = (-this.vx);
        this.vx *= 1.0D;
      }
      if (this.x > 100.0D - this.radius)
      {
        this.x = (100.0D - this.radius);
        this.vx = (-this.vx);
        this.vx *= 1.0D;
      }
      if (this.y < 0.0D + this.radius)
      {
        this.y = (0.0D + this.radius);
        this.vy = (-this.vy);
        if (this.bomb) {
          this.vy *= 0.9999D;
        }
      }
      if (this.y > 100.0D - this.radius)
      {
        this.y = (100.0D - this.radius);
        this.vy = (-this.vy);
      }
      if (this.z < -100.0D + this.radius)
      {
        this.z = (-100.0D + this.radius);
        this.vz = (-this.vz);
        this.vz *= 1.0D;
      }
      if (this.z > 100.0D - this.radius)
      {
        this.z = (100.0D - this.radius);
        this.vz = (-this.vz);
        this.vz *= 1.0D;
      }
      if (this.bomb)
      {
        this.totalTime += dt;
        this.radius = (this.initialRadius - this.totalTime / 5000.0D);
      }
    }
  }
  
  public class Camera
  {
    double ex;
    double ey;
    double ez;
    double cx;
    double cy;
    double cz;
    double ux;
    double uy;
    double uz;
    double theta = 0.087266463D;
    
    Camera(double ex, double ey, double ez, double cx, double cy, double cz, double ux, double uy, double uz)
    {
      this.ex = ex;
      this.ey = ey;
      this.ez = ez;
      this.cx = cx;
      this.cy = cy;
      this.cz = cz;
      this.ux = ux;
      this.uy = uy;
      this.uz = uz;
    }
    
    void setLookAt(GLU glu)
    {
      glu.gluLookAt(this.ex, this.ey, this.ez, this.cx, this.cy, this.cz, this.ux, this.uy, this.uz);
    }
    
    void forward()
    {
      double dx = this.cx - this.ex;double dy = this.cy - this.ey;double dz = this.cz - this.ez;
      double m = Math.sqrt(GameTest.this.sqr(dx) + GameTest.this.sqr(dy) + GameTest.this.sqr(dz));
      dx /= m / 2.0D;
      dy /= m / 2.0D;
      dz /= m / 2.0D;
      
      this.cx += dx;
      this.cy += dy;
      this.cz += dz;
      this.ex += dx;
      this.ey += dy;
      this.ez += dz;
    }
    
    void back()
    {
      double dx = this.cx - this.ex;double dy = this.cy - this.ey;double dz = this.cz - this.ez;
      double m = Math.sqrt(GameTest.this.sqr(dx) + GameTest.this.sqr(dy) + GameTest.this.sqr(dz));
      dx /= m / 2.0D;
      dy /= m / 2.0D;
      dz /= m / 2.0D;
      
      this.cx -= dx;
      this.cy -= dy;
      this.cz -= dz;
      this.ex -= dx;
      this.ey -= dy;
      this.ez -= dz;
    }
    
    void left()
    {
      double dx = this.cx - this.ex;double dy = this.cy - this.ey;double dz = this.cz - this.ez;
      double m = Math.sqrt(GameTest.this.sqr(dx) + GameTest.this.sqr(dy) + GameTest.this.sqr(dz));
      dx /= m;
      dy /= m;
      dz /= m;
      double nx = Math.cos(this.theta) * dx + Math.sin(this.theta) * dz;
      double ny = dy;
      double nz = -Math.sin(this.theta) * dx + Math.cos(this.theta) * dz;
      this.cx = (this.ex + nx);
      this.cy = (this.ey + ny);
      this.cz = (this.ez + nz);
    }
    
    void right()
    {
      double dx = this.cx - this.ex;double dy = this.cy - this.ey;double dz = this.cz - this.ez;
      double m = Math.sqrt(GameTest.this.sqr(dx) + GameTest.this.sqr(dy) + GameTest.this.sqr(dz));
      dx /= m;
      dy /= m;
      dz /= m;
      double nx = Math.cos(this.theta) * dx - Math.sin(this.theta) * dz;
      double ny = dy;
      double nz = Math.sin(this.theta) * dx + Math.cos(this.theta) * dz;
      this.cx = (this.ex + nx);
      this.cy = (this.ey + ny);
      this.cz = (this.ez + nz);
    }
  }
  
  int winHeight = 525;
  int winWidth = 700;
  boolean paused = false;
  Thread updateThread;
  FPSAnimator animator;
  GLU glu;
  ArrayList<Sphere> targets;
  ArrayList<Sphere> bombs;
  KDOigleTree test;
  GLUT glut;
  Camera camera;
  
  public GameTest()
  {
    this.camera = new Camera(0.0D, 25.0D, 0.0D, 0.0D, 25.0D, 1.0D, 0.0D, 1.0D, 0.0D);
    this.targets = new ArrayList<Sphere>();
    this.bombs = new ArrayList<Sphere>();
    for (int i = 0; i < 10; i++) {
      this.targets.add(new Sphere((Math.random() - 0.5D) * 200.0D, Math.random() * 100.0D, (Math.random() - 0.5D) * 200.0D, 
        0.25D * (Math.random() - 0.5D), 0.25D * (Math.random() - 0.5D), 0.25D * (Math.random() * 0.5D), 
        0.7D, 0.3D, 0.7D, 5.0D, false));
    }
  }
  
  public synchronized void update()
  {
    if (this.paused) {
      return;
    }
    ListIterator<Sphere> li = this.targets.listIterator();
    while (li.hasNext()) {
      ((Sphere)li.next()).update();
    }
    li = this.bombs.listIterator();
    while (li.hasNext())
    {
      Sphere i = (Sphere)li.next();
      i.update();
      if (i.radius < 0.0D) {
        li.remove();
      }
    }
    li = this.targets.listIterator();
    ListIterator<Sphere> lj;
    for (; li.hasNext(); lj.hasNext())
    {
        lj = this.bombs.listIterator();
        Sphere i = (Sphere)li.next();

        while(lj.hasNext()) 
        {
            Sphere j = (Sphere)lj.next();
            
            if (sqr(i.x - j.x) + sqr(i.y - j.y) + sqr(i.z - j.z) < sqr(i.radius + j.radius))
            {
                li.remove();
                lj.remove();
            }
        }
    }
  }

  public synchronized void display(GLAutoDrawable gld)
  {
    GL2 gl = gld.getGL().getGL2();
    gl.glClear(16640);
    gl.glLoadIdentity();
    this.camera.setLookAt(this.glu);
    float[] pos = { 0.0F, 75.0F, 0.0F, 1.0F };
    gl.glLightfv(16385, 4611, pos, 0);
    float[] diffuse = { 0.7F, 0.7F, 0.7F, 1.0F };
    gl.glLightfv(16385, 4609, diffuse, 0);
    float[] ambient = { 0.2F, 0.2F, 0.2F, 1.0F };
    gl.glLightfv(16385, 4608, ambient, 0);
    
    gl.glBegin(7);
    float[] color1 = { 0.5F, 0.8F, 1.0F, 1.0F };
    float[] color2 = { 0.6F, 0.8F, 0.7F, 1.0F };
    float[] color3 = { 1.0F, 1.0F, 0.8F, 1.0F };
    
    gl.glMaterialfv(1032, 5634, color1, 0);
    gl.glNormal3d(0.0D, 0.0D, 1.0D);
    gl.glVertex3d(-100.0D, 0.0D, -100.0D);
    gl.glVertex3d(100.0D, 0.0D, -100.0D);
    gl.glVertex3d(100.0D, 100.0D, -100.0D);
    gl.glVertex3d(-100.0D, 100.0D, -100.0D);
    
    gl.glMaterialfv(1032, 5634, color3, 0);
    gl.glNormal3d(0.0D, 1.0D, 0.0D);
    gl.glVertex3d(-100.0D, 0.0D, -100.0D);
    gl.glVertex3d(-100.0D, 0.0D, 100.0D);
    gl.glVertex3d(100.0D, 0.0D, 100.0D);
    gl.glVertex3d(100.0D, 0.0D, -100.0D);
    
    gl.glMaterialfv(1032, 5634, color2, 0);
    gl.glNormal3d(1.0D, 0.0D, 0.0D);
    gl.glVertex3d(-100.0D, 0.0D, -100.0D);
    gl.glVertex3d(-100.0D, 100.0D, -100.0D);
    gl.glVertex3d(-100.0D, 100.0D, 100.0D);
    gl.glVertex3d(-100.0D, 0.0D, 100.0D);
    
    gl.glMaterialfv(1032, 5634, color1, 0);
    gl.glNormal3d(0.0D, 0.0D, -1.0D);
    gl.glVertex3d(-100.0D, 0.0D, 100.0D);
    gl.glVertex3d(-100.0D, 100.0D, 100.0D);
    gl.glVertex3d(100.0D, 100.0D, 100.0D);
    gl.glVertex3d(100.0D, 0.0D, 100.0D);
    
    gl.glMaterialfv(1032, 5634, color3, 0);
    gl.glNormal3d(0.0D, -1.0D, 0.0D);
    gl.glVertex3d(-100.0D, 100.0D, -100.0D);
    gl.glVertex3d(100.0D, 100.0D, -100.0D);
    gl.glVertex3d(100.0D, 100.0D, 100.0D);
    gl.glVertex3d(-100.0D, 100.0D, 100.0D);
    
    gl.glMaterialfv(1032, 5634, color2, 0);
    gl.glNormal3d(-1.0D, 0.0D, 0.0D);
    gl.glVertex3d(100.0D, 0.0D, -100.0D);
    gl.glVertex3d(100.0D, 0.0D, 100.0D);
    gl.glVertex3d(100.0D, 100.0D, 100.0D);
    gl.glVertex3d(100.0D, 100.0D, -100.0D);
    
    gl.glEnd();
    
    ListIterator<Sphere> li = this.targets.listIterator();
    while (li.hasNext()) {
      ((Sphere)li.next()).draw(gld, this.glut);
    }
    li = this.bombs.listIterator();
    while (li.hasNext()) {
      ((Sphere)li.next()).draw(gld, this.glut);
    }
  }
  
  public void displayChanged(GLAutoDrawable gld, boolean arg1, boolean arg2) {}
  
  public void reshape(GLAutoDrawable gld, int x, int y, int width, int height)
  {
    GL gl = gld.getGL();
    this.winWidth = width;
    this.winHeight = height;
    gl.glViewport(0, 0, width, height);
  }
  
  public void mouseClicked(MouseEvent e) {}
  
  public void mouseEntered(MouseEvent e) {}
  
  public void mouseExited(MouseEvent e) {}
  
  public void mousePressed(MouseEvent e) {}
  
  public void mouseReleased(MouseEvent e) {}
  
  public void mouseDragged(MouseEvent e) {}
  
  public void keyReleased(KeyEvent e) {}
  
  public void keyTyped(KeyEvent e) {}
  
  public void mouseMoved(MouseEvent e) {}
  
  public synchronized void keyPressed(KeyEvent e)
  {
    switch (e.getKeyCode())
    {
    case 38: 
      this.camera.forward();
      break;
    case 40: 
      this.camera.back();
      break;
    case 37: 
      this.camera.left();
      break;
    case 39: 
      this.camera.right();
      break;
    case 32: 
      this.bombs.add(new Sphere(this.camera.ex, this.camera.ey, this.camera.ez, 0.1D * (this.camera.cx - this.camera.ex), 0.1D, 0.1D * (this.camera.cz - this.camera.ez), 0.8D, 0.4D, 0.4D, 2.0D, true));
    }
  }
  
  public void init(GLAutoDrawable gld)
  {
    this.glu = new GLU();
    this.glut = new GLUT();
    GL2 gl = gld.getGL().getGL2();
    gl.glClearColor(0.8F, 0.8F, 0.8F, 1.0F);
    gl.glMatrixMode(5889);
    gl.glLoadIdentity();
    this.glu.gluPerspective(60.0D, 1.33D, 0.01D, 1000.0D);
    gl.glMatrixMode(5888);
    gl.glLoadIdentity();
    gl.glEnable(2977);
    gl.glEnable(2884);
    gl.glEnable(2929);
    gl.glEnable(2896);
    gl.glEnable(16385);
    gl.glShadeModel(7425);
  }
  
  public void init()
  {
    setLayout(new FlowLayout());
    
    GLProfile glp = GLProfile.getDefault();
    GLCapabilities caps = new GLCapabilities(glp);
    GLCanvas canvas = new GLCanvas(caps);
    canvas.setPreferredSize(new Dimension(this.winWidth, this.winHeight));
    

    canvas.addGLEventListener(this);
    canvas.addMouseListener(this);
    canvas.addMouseMotionListener(this);
    canvas.addKeyListener(this);
    add(canvas);
    setSize(this.winWidth, this.winHeight);
    
    this.animator = new FPSAnimator(canvas, 60);
    this.updateThread = new Thread(new Runnable()
    {
      public void run()
      {
        try
        {
          for (;;)
          {
            GameTest.this.update();
            Thread.sleep(2L);
          }
        }
        catch (Exception localException) {}
      }
    });
  }
  
  public void start()
  {
    this.animator.start();
    this.updateThread.start();
  }
  
  public void stop()
  {
    this.animator.stop();
    this.updateThread.interrupt();
  }
  
  public void dispose(GLAutoDrawable arg0) {}
}
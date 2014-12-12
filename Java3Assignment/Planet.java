import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.behaviors.mouse.*;
import com.sun.j3d.utils.behaviors.vp.*;

public class Planet extends Applet{
    private SimpleUniverse u=null;

    public static void main(String[] argv){
	new MainFrame(new Planet(), 640,520);
    }
    public Planet(){
    }

    public void init(){

	//Prepare 3D Canvas
	setLayout(new BorderLayout());
	GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
	Canvas3D c = new Canvas3D(config);
	add("Center",c);

	u = new SimpleUniverse(c);

	//Create Scene Graph which contains 3D Sphere object
	BranchGroup scene = createSceneGraph();
	//Create Directional Light to show Sphere geometry
	BranchGroup lightGroup = createDirectionalLight();
	
	//Transmit camera from Center
	u.getViewingPlatform().setNominalViewingTransform();

		
	// Orbit behavior
    OrbitBehavior ob = new OrbitBehavior(c, OrbitBehavior.REVERSE_ALL);
    BoundingSphere bs = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
    ob.setSchedulingBounds(bs);
		
    u.getViewingPlatform().setViewPlatformBehavior(ob);
		
		
	//Add BranchGraph scene to SimpleUniverse
	u.addBranchGraph(scene);
		u.addBranchGraph(lightGroup);
    }

    public BranchGroup createSceneGraph(){
	//Create root of BranchGroup
		BranchGroup root = new BranchGroup();

	//it is necessary to Transform and animation
		TransformGroup objTrans = new TransformGroup();
		objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		root.addChild(objTrans);

		
		
		
		//Setting of Material
		Appearance ap = createAppearance();
		
		
		
		//Create and Set Sphere geometry
		int primflags = Primitive.GENERATE_NORMALS|Primitive.GENERATE_TEXTURE_COORDS;
		objTrans.addChild(new Sphere(0.5F,primflags,35,ap));
		
		
		Transform3D yAxis = new Transform3D();
		Alpha rotationAlpha = new Alpha(-1, 4000);
		RotationInterpolator rotator = new RotationInterpolator(rotationAlpha, objTrans, yAxis, 0.0f, (float)Math.PI*2.0f);
		
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
		rotator.setSchedulingBounds(bounds);
		root.addChild(rotator);
		root.compile();

		return root;
    }

	public Appearance createAppearance(){
		Appearance ap = new Appearance();
		Material material = new Material();
		
		
		//material.setAmbientColor(0.8F,0.8F,0.8F);
		material.setDiffuseColor(1.3F,1.3F,1.3F);
		//material.setEmissiveColor(0.5F, 0.5F, 0.5F);
		material.setSpecularColor(1.5f,1.5f, 1.5F);
		ap.setMaterial(material);
		
		//Load Texture
		TextureLoader texloader = new TextureLoader("EarthMap_1250x625.jpg", this);
		Texture2D tex = (Texture2D)texloader.getTexture();
		ap.setTexture(tex);
		
		
        // texture attributes
        TextureAttributes texAttrib = new TextureAttributes();
        texAttrib.setTextureMode(TextureAttributes.MODULATE);
        ap.setTextureAttributes(texAttrib);
		
        // assign PolygonAttributes to the Appearance.
        PolygonAttributes polyAttrib = new PolygonAttributes();
        polyAttrib.setCullFace(PolygonAttributes.CULL_NONE);
        polyAttrib.setBackFaceNormalFlip(true);    
        ap.setPolygonAttributes(polyAttrib);
		
		
		return ap;
	}
	
	
	public BranchGroup createDirectionalLight(){
		Color3f light_color = new Color3f(1.0f,1.0f,1.0f);
		//Vector3f light_direction = new Vector3f(0.2f,-0.2f,-0.6f);
		Vector3f light_direction = new Vector3f(1.5f,-0.8f,-0.8f);
		DirectionalLight light = new DirectionalLight(light_color, light_direction);
		
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
		
		light.setInfluencingBounds(bounds);
		
		BranchGroup group2 = new BranchGroup();

		group2.addChild(light);
		
		return group2;
	}


}
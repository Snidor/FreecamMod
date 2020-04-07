package freecammod;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.gotti.wurmunlimited.modloader.ReflectionUtil;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.interfaces.Initable;
import org.gotti.wurmunlimited.modloader.interfaces.WurmClientMod;

import com.wurmonline.client.game.World;
import com.wurmonline.client.renderer.gui.HeadsUpDisplay;

import javassist.ClassPool;
import javassist.CtClass;

public class FreecamMod implements WurmClientMod, Initable
{
	public static HeadsUpDisplay mHud;
	public static World mWorld;
	public static Logger mLogger = Logger.getLogger( "FreecamMod" );	
	private static boolean mFreecam = false;
	
	public static boolean handleInput( final String pCommand, final String[] data ) 
	{
		if ( pCommand.toLowerCase().equals("togglefreecam") ) 
		{
			mFreecam = !mFreecam;
			mWorld.getWorldRenderer().toggleFreeCamera();
			mHud.consoleOutput( "[FreecamMod] enabled Freecam: " + Boolean.toString( mFreecam ) );
			return true;
		}
		return false;
	}

	@Override
	public void init() 
	{
		mLogger.log( Level.INFO, "Init FreecamMod" );
		try 
		{
			ClassPool lClassPool = HookManager.getInstance().getClassPool();

			CtClass lCtWurmConsole = lClassPool.getCtClass( "com.wurmonline.client.console.WurmConsole" );
			lCtWurmConsole.getMethod( "handleDevInput", "(Ljava/lang/String;[Ljava/lang/String;)Z" ).insertBefore("if (freecammod.FreecamMod.handleInput($1,$2)) return true;");
			
			HookManager.getInstance().registerHook( "com.wurmonline.client.renderer.gui.HeadsUpDisplay", "init", "(II)V", () -> ( pProxy, pMethod, pArgs ) -> 
			{
				pMethod.invoke( pProxy, pArgs );
				mHud = ( HeadsUpDisplay ) pProxy;
				return null;
			});
			
			HookManager.getInstance().registerHook( "com.wurmonline.client.renderer.WorldRender", "renderPickedItem", "(Lcom/wurmonline/client/renderer/backend/Queue;)V", () -> ( pProxy, pMethod, pArgs ) -> 
			{
				pMethod.invoke(pProxy, pArgs);
				Class<?> lCls = pProxy.getClass();

				mWorld = ReflectionUtil.getPrivateField( pProxy, ReflectionUtil.getField( lCls, "world" ) );

				return null;
			});
		} 
		catch ( Throwable e ) 
		{
			mLogger.log( Level.SEVERE, "Error FreecamMod", e.getMessage() );
		}
	}

}

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
	
	public static boolean mLockedX = false;
	public static boolean mLockedY = false;
	public static boolean mLockedZ = false;
	public static boolean mLockedRotX = false;
	public static boolean mLockedRotY = false;
	
	public static float mCameraX = 0f;
	public static float mCameraY = 0f;
	public static float mCameraZ = 0f;
	public static float mCameraRotX = 0f;
	public static float mCameraRotY = 0f;
	
	public static float mQSCameraX = 0f;
	public static float mQSCameraY = 0f;
	public static float mQSCameraZ = 0f;
	public static float mQSCameraRotX = 0f;
	public static float mQSCameraRotY = 0f;
	
	public static boolean mQSAvailable = false;
	
	public static boolean handleInput( final String pCommand, final String[] pData ) 
	{
		if ( pCommand.toLowerCase().equals( "togglefreecam" ) ) 
		{
			mFreecam = !mFreecam;
			
			mLockedX = false;
			mLockedY = false;
			mLockedZ = false;
			mLockedRotX = false;
			mLockedRotY = false;
			
			mWorld.getWorldRenderer().toggleFreeCamera();
			mHud.consoleOutput( "[FreecamMod] enabled Freecam: " + Boolean.toString( mFreecam ) );
			return true;
		}
		if ( mFreecam )
		{
			if ( pCommand.toLowerCase().equals( "lockcam" ) ) 
			{
				if ( pData.length == 2) 
				{
					if ( pData[1].equalsIgnoreCase( "ALL" ) )
					{
						mLockedX = true;
						mLockedY = true;
						mLockedZ = true;
						mLockedRotX = true;
						mLockedRotY = true;
						mLogger.log( Level.INFO, "CAM LOCKED ALL AXES: \nX: " + mCameraX + "\nY: " + mCameraY + "\nZ: " + mCameraZ + "\nRotX: " + mCameraRotX + "\nRotY: " + mCameraRotY );	
					}
					else
					{
						if ( pData[1].contains( "X" ) )
						{
							mLockedX = true;
							mLogger.log( Level.INFO, "CAM LOCKED X AXIS: " + mCameraX );	
						}
						if ( pData[1].contains( "Y" ) )
						{
							mLockedY = true;
							mLogger.log( Level.INFO, "CAM LOCKED Y AXIS: " + mCameraY );
						}
						if ( pData[1].contains( "Z" ) )
						{
							mLockedZ = true;
							mLogger.log( Level.INFO, "CAM LOCKED Z AXIS: " + mCameraZ );
						}
						if ( pData[1].contains( "H" ) )
						{
							mLockedRotX = true;
							mLogger.log( Level.INFO, "CAM LOCKED H AXIS: " + mCameraRotX );
						}
						if ( pData[1].contains( "V" ) )
						{
							mLockedRotY = true;
							mLogger.log( Level.INFO, "CAM LOCKED V AXIS: " + mCameraRotY );
						}							
					}
				}		
				return true;
			}
			else if ( pCommand.toLowerCase().equals( "unlockcam" ) ) 
			{
				if ( pData.length == 2) 
				{
					if ( pData[1].equalsIgnoreCase( "ALL" ) )
					{
						mLockedX = false;
						mLockedY = false;
						mLockedZ = false;
						mLockedRotX = false;
						mLockedRotY = false;
						mLogger.log( Level.INFO, "CAM UNLOCKED ALL AXES: \nX: " + mCameraX + "\nY: " + mCameraY + "\nZ: " + mCameraZ + "\nRotX: " + mCameraRotX + "\nRotY: " + mCameraRotY );	
					}
					else
					{
						if ( pData[1].contains( "X" ) )
						{
							mLockedX = false;
							mLogger.log( Level.INFO, "CAM UNLOCKED X AXIS: " + mCameraX );
						}
						if ( pData[1].contains( "Y" ) )
						{
							mLockedY = false;
							mLogger.log( Level.INFO, "CAM UNLOCKED Y AXIS: " + mCameraY );
						}
						if ( pData[1].contains( "Z" ) )
						{
							mLockedZ = false;
							mLogger.log( Level.INFO, "CAM UNLOCKED Z AXIS: " + mCameraZ );
						}
						if ( pData[1].contains( "H" ) )
						{
							mLockedRotX = false;
							mLogger.log( Level.INFO, "CAM UNLOCKED H AXIS: " + mCameraRotX );
						}
						if ( pData[1].contains( "V" ) )
						{
							mLockedRotY = false;
							mLogger.log( Level.INFO, "CAM UNLOCKED V AXIS: " + mCameraRotY );
						}							
					}
				}		
				return true;
			}
			else if ( pCommand.toLowerCase().equals( "dumpcam" ) ) 
			{
				mLogger.log( Level.INFO, "CAMSETTINGS: \nX: " + mCameraX + "\nY: " + mCameraY + "\nZ: " + mCameraZ + "\nRotX: " + mCameraRotX + "\nRotY: " + mCameraRotY );	
				return true;
			}
			else if ( pCommand.toLowerCase().equals( "setcam" ) ) 
			{
				if ( pData.length == 3 ) 
				{
					if ( pData[1].contains( "X" ) )
					{
						mCameraX = Float.parseFloat( pData[2] );
						mLockedX = true;
						mLogger.log( Level.INFO, "CAM LOCKED X AXIS AND SET TO: " + mCameraX );
					}
					if ( pData[1].contains( "Y" ) )
					{
						mCameraY = Float.parseFloat( pData[2] );
						mLockedY = true;
						mLogger.log( Level.INFO, "CAM LOCKED Y AXIS AND SET TO: " + mCameraY );
					}
					if ( pData[1].contains( "Z" ) )
					{
						mCameraZ = Float.parseFloat( pData[2] );
						mLockedZ = true;
						mLogger.log( Level.INFO, "CAM LOCKED Z AXIS AND SET TO: " + mCameraZ );
					}
					if ( pData[1].contains( "H" ) )
					{
						mCameraRotX = Float.parseFloat( pData[2] );
						mLockedRotX = true;
						mLogger.log( Level.INFO, "CAM LOCKED H AXIS AND SET TO: " + mCameraRotX );
					}
					if ( pData[1].contains( "V" ) )
					{
						mCameraRotY = Float.parseFloat( pData[2] );
						mLockedRotY = true;
						mLogger.log( Level.INFO, "CAM LOCKED V AXIS AND SET TO: " + mCameraRotY );
					}							
				}		
				return true;
			}
			else if ( pCommand.toLowerCase().equals( "qscam" ) ) 
			{
				mQSCameraX = mCameraX;
				mQSCameraY = mCameraY;
				mQSCameraZ = mCameraZ;
				mQSCameraRotX = mCameraRotX;
				mQSCameraRotY = mCameraRotY;
				mQSAvailable = true;
				mLogger.log( Level.INFO, "CAM QUICKSAVE ALL AXES: \nX: " + mQSCameraX + "\nY: " + mQSCameraY + "\nZ: " + mQSCameraZ + "\nRotX: " + mQSCameraRotX + "\nRotY: " + mQSCameraRotY );
				return true;
			}
			else if ( pCommand.toLowerCase().equals( "qlcam" ) ) 
			{
				if ( ( pData.length == 2 ) && ( mQSAvailable ) ) 
				{
					if ( pData[1].contains( "X" ) )
					{
						mCameraX = mQSCameraX;
						mLockedX = true;
						mLogger.log( Level.INFO, "CAM QUICKLOAD AND LOCKED X AXIS AND SET TO: " + mCameraX );
					}
					if ( pData[1].contains( "Y" ) )
					{
						mCameraY = mQSCameraY;
						mLockedY = true;
						mLogger.log( Level.INFO, "CAM QUICKLOAD AND LOCKED Y AXIS AND SET TO: " + mCameraY );
					}
					if ( pData[1].contains( "Z" ) )
					{
						mCameraZ = mQSCameraZ;
						mLockedZ = true;
						mLogger.log( Level.INFO, "CAM QUICKLOAD AND LOCKED Z AXIS AND SET TO: " + mCameraZ );
					}
					if ( pData[1].contains( "H" ) )
					{
						mCameraRotX = mQSCameraRotX;
						mLockedRotX = true;
						mLogger.log( Level.INFO, "CAM QUICKLOAD AND LOCKED H AXIS AND SET TO: " + mCameraRotX );
					}
					if ( pData[1].contains( "V" ) )
					{
						mCameraRotY = mQSCameraRotY;
						mLockedRotY = true;
						mLogger.log( Level.INFO, "CAM QUICKLOAD AND LOCKED V AXIS AND SET TO: " + mCameraRotY );
					}							
				}	
				return true;
			}		
		}
		return false;
	}
	
	public static boolean isCamLocked()
	{
		boolean lReturn = mLockedX || mLockedY || mLockedZ || mLockedRotX || mLockedRotY;
		return lReturn;
	}
	
	public static void setCam( float pX, float pY, float pZ, float pRX, float pRY )
	{
		if ( !mLockedX ) mCameraX = pX;
		if ( !mLockedY ) mCameraY = pY;
		if ( !mLockedZ ) mCameraZ = pZ;
		if ( !mLockedRotX ) mCameraRotX = pRX;
		if ( !mLockedRotY ) mCameraRotY = pRY;
	}

	@Override
	public void init() 
	{
		mLogger.log( Level.INFO, "Init FreecamMod" );
		try 
		{
			ClassPool lClassPool = HookManager.getInstance().getClassPool();

			CtClass lCtWurmConsole = lClassPool.getCtClass( "com.wurmonline.client.console.WurmConsole" );
			lCtWurmConsole.getMethod( "handleDevInput", "(Ljava/lang/String;[Ljava/lang/String;)Z" ).insertBefore( "if (freecammod.FreecamMod.handleInput($1,$2)) return true;" );
			
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
			
			CtClass lCtWorldRenderer = lClassPool.getCtClass( "com.wurmonline.client.renderer.WorldRender" );
			lCtWorldRenderer.getMethod( "calculateCamera", "()V" ).insertBefore( "freecammod.FreecamMod.setCam(this.cameraX, this.cameraZ, this.cameraY, this.cameraRotX, this.cameraRotY);" );
			lCtWorldRenderer.getMethod( "calculateCamera", "()V" ).insertAfter( "if ( freecammod.FreecamMod.mLockedX ) this.cameraX = freecammod.FreecamMod.mCameraX;" );
			lCtWorldRenderer.getMethod( "calculateCamera", "()V" ).insertAfter( "if ( freecammod.FreecamMod.mLockedY ) this.cameraZ = freecammod.FreecamMod.mCameraY;" );
			lCtWorldRenderer.getMethod( "calculateCamera", "()V" ).insertAfter( "if ( freecammod.FreecamMod.mLockedZ ) this.cameraY = freecammod.FreecamMod.mCameraZ;" );
			lCtWorldRenderer.getMethod( "calculateCamera", "()V" ).insertAfter( "if ( freecammod.FreecamMod.mLockedRotX ) this.cameraRotX = freecammod.FreecamMod.mCameraRotX;" );
			lCtWorldRenderer.getMethod( "calculateCamera", "()V" ).insertAfter( "if ( freecammod.FreecamMod.mLockedRotY ) this.cameraRotY = freecammod.FreecamMod.mCameraRotY;" );

			lCtWorldRenderer.getMethod( "turnCamera", "(II)V" ).insertBefore( "freecammod.FreecamMod.setCam(this.cameraX, this.cameraZ, this.cameraY, this.cameraRotX, this.cameraRotY);" );
			lCtWorldRenderer.getMethod( "turnCamera", "(II)V" ).insertAfter( "if ( freecammod.FreecamMod.mLockedRotX ) this.cameraRotX = freecammod.FreecamMod.mCameraRotX;" );
			lCtWorldRenderer.getMethod( "turnCamera", "(II)V" ).insertAfter( "if ( freecammod.FreecamMod.mLockedRotY ) this.cameraRotY = freecammod.FreecamMod.mCameraRotY;" );
			
		} 
		catch ( Throwable e ) 
		{
			mLogger.log( Level.SEVERE, "Error FreecamMod", e.getMessage() );
		}
	}
}

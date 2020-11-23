# FreecamMod
Open the console window (F1) and enter
togglefreecam
to toggle the freecam

if you want to create a keybind to toggle them. DON'T use CTRL, SHIFT or ALT as modifier, because they are already used by default for this features:
ALT: by pressed ALT key, the movement speed of the freecam is slowed down.
SHIFT: by pressed SHIFT key, the movement speed of the freecam is speeded up.
CTRL: by pressed CTRL key, you can control your character while in freecam mode.


while in freecam mode, there are a few possible commands too.
but before they will be explained, here is a little definition for the axes:
X-axis: the axis from west to east.
Y-axis: the axis from north to south.
Z-axis: the axis from ground to heaven.
H-axis: the axis for the horizontal view.
V-axis: the axis for the vertical view.


commands while in freecam mode:

lockcam <AXES or ALL>		lock the defined axes. 
	example->lockcam XZ
	this would lock the X and Z axes.

unlockcam <AXES or ALL>		unlock the defined axes.
	example->unlockcam ALL
	this would unlock all axes.

dumpcam 			log the current camera settings in console and the logfile.

setcam <AXIS> <VALUE>		define and lock the defined axis with the defined value.
	example->setcam X 1503.2043
	this would set and lock the X-axis to 1503.2043

qscam                		save the current camera settings for this session. will be overridden by recalling the function again.

qlcam <AXES>			load and lock the defined axes from the qscam values;
	example->qlcam VH
	this would load and lock the V and H axes.

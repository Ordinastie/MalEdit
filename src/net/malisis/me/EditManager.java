package net.malisis.me;


public class EditManager
{
	private static EditManager instance;
	
	/**
	 * Stores last 5 editions
	 */
	private Edit[] edits = new Edit[5];
	private Edit currentEdit;
	
	private EditManager()
	{
		instance = this;
	}
	
	public static EditManager getManager()
	{
		if(instance == null)
			instance = new EditManager();
		return instance;
	}
	
	public Edit getCurrentEdit()
	{
		if(currentEdit == null)
			currentEdit = new Edit();
		return currentEdit;
	}
	
}

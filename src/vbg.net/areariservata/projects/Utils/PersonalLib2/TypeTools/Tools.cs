using System;

namespace PersonalLib2.TypeTools
{
	/// <summary>
	/// Utility per gli oggetti di tipo Type.
	/// </summary>
	public class Tools
	{
		public Tools()
		{
		}

		/// <summary>
		/// Ritorna true se il tipo baseType è derivato da parentBaseType.
		/// Itera in baseType.BaseType per cercare se baseType deriva da parentBaseType.
		/// </summary>
		/// <param name="baseType">E' il valore della proprietà BaseType dell'oggetto da controllare.</param>
		/// <param name="parentBaseType">E' il tipo di base dal quale può derivare baseType.</param>
		/// <returns>True se baseType deriva da parentBaseType.</returns>
		public static bool GetNestedBaseType(Type baseType, Type parentBaseType)
		{
			Type myType = baseType;
			while (myType.BaseType!=parentBaseType && myType.BaseType!=null)
			{
				myType = myType.BaseType;
			}

			return (myType.BaseType!=null);
		}
	}
}

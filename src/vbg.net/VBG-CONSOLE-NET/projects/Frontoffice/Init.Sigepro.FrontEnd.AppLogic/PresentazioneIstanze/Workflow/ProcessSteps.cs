using System.Collections.Generic;
using System;
using System.Linq;


namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow
{
	public partial class StepCollectionType
	{
		public bool IsFirstStep(int stepId)
		{
			return stepId == 0;
		}
		public bool IsLastStep(int stepId)
		{
			return stepId >= (Step.Length - 1);
		}
		public string GetStepUrl(int stepId)
		{
			if (stepId > (Step.Length - 1))
				return "InvalidStep.aspx";

			return Step[stepId].Control;
		}
		public IEnumerable<string> GetTitoliSteps()
		{
			for (int i = 0; i < Step.Length; i++)
				yield return Step[i].Title;
		}
	}

	public partial class StepType
	{
		/// <summary>
		/// Implementa la deep copy dello step corrente
		/// </summary>
		/// <returns></returns>
		public StepType Clone()
		{
			return new StepType
			{
				Control = this.Control,
				ControlProperty = this.ControlProperty == null ? 
									new PropertyValueType[0] : 
									this.ControlProperty.Select(x => x.Clone()).ToArray(),
				Description = this.Description,
				SummaryControl = this.SummaryControl,
				Title = this.Title				
			};

		}

		public override bool Equals(object obj)
		{
			if (obj == null) return false;
			if (this.GetType() != obj.GetType()) return false;

			var typedObj = obj as StepType;

			if (!this.Title.Equals(typedObj.Title))
				return false;

			if (!this.Control.Equals(typedObj.Control))
				return false;

			if (!this.Description.Equals(typedObj.Description))
				return false;

			if (!this.SummaryControl.Equals(typedObj.SummaryControl))
				return false;

			if (this.ControlProperty != null && typedObj.ControlProperty == null)
				return false;

			if (this.ControlProperty == null && typedObj.ControlProperty != null)
				return false;

			if (this.ControlProperty == null && typedObj.ControlProperty == null)
				return true;

			if (this.ControlProperty.Length != typedObj.ControlProperty.Length)
				return false;

			for (int i = 0; i < this.ControlProperty.Length; i++)
			{
				if (!this.ControlProperty[i].Equals(typedObj.ControlProperty[i]))
					return false;
			}

			return true;
		}

		public override int GetHashCode()
		{
			return this.Title.GetHashCode() ^ this.Control.GetHashCode();
		}

		public static Boolean operator ==(StepType v1, StepType v2)
		{

			if ((object)v1 == null)
				if ((object)v2 == null)
					return true;
				else
					return false;

			return (v1.Equals(v2));
		}

		public static Boolean operator !=(StepType v1, StepType v2)
		{
			return !(v1 == v2);
		}

	}


	public partial class PropertyValueType
	{
				/// <summary>
		/// Implementa la deep copy dello step corrente
		/// </summary>
		/// <returns></returns>
		public PropertyValueType Clone()
		{
			return new PropertyValueType{ name = this.name , Value = this.Value };
		}

		public override bool Equals(object obj)
		{
			if (obj == null) return false;
			if (this.GetType() != obj.GetType()) return false;

			var typedObj = obj as PropertyValueType;

			if (!this.name.Equals(typedObj.name))
				return false;

			if (!this.Value.Equals(typedObj.Value))
				return false;

			return true;
		}

		public override int GetHashCode()
		{
			return this.name.GetHashCode() ^ this.Value.GetHashCode();
		}

		public static Boolean operator ==(PropertyValueType v1, PropertyValueType v2)
		{

			if ((object)v1 == null)
				if ((object)v2 == null)
					return true;
				else
					return false;

			return (v1.Equals(v2));
		}

		public static Boolean operator !=(PropertyValueType v1, PropertyValueType v2)
		{
			return !(v1 == v2);
		}

	}

}

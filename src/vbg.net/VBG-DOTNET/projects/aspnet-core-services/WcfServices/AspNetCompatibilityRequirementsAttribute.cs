namespace System.ServiceModel.Activation
{
    public enum AspNetCompatibilityRequirementsMode
    {
        Allowed
    }
    public class AspNetCompatibilityRequirementsAttribute : Attribute
    {
        public AspNetCompatibilityRequirementsAttribute() { }

        public AspNetCompatibilityRequirementsMode RequirementsMode { get; set; }
    }
}

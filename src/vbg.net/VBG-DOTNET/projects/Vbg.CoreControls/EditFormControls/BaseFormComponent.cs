using Microsoft.AspNetCore.Components;
using Microsoft.AspNetCore.Components.Forms;
using System.Diagnostics.CodeAnalysis;
using System.Globalization;
using System.Linq.Expressions;

namespace Vbg.CoreControls.EditFormControls
{
    public abstract class BaseFormComponent<TValue> : ComponentBase//, IDisposable
    {
    //    [CascadingParameter]
    //    public EditContext editContext { get; set; }

    //    public ValidationMessageStore? messageStore { get; set; }
    //    protected FieldIdentifier fieldIdentifier { get; private set; }
    //    protected ElementReference elementReference;
    //    private Type? _nullableUnderlyingType;
    //    private bool _previousParsingAttemptFailed;
    //    private bool _hasInitializedParameters;

    //    protected bool IsNullableType
    //    {
    //        get => _nullableUnderlyingType != null;
    //    }

    //    [Parameter(CaptureUnmatchedValues = true)] public IReadOnlyDictionary<string, object>? AdditionalAttributes { get; set; }

    //    [Parameter]
    //    public TValue? Value { get; set; }

    //    [Parameter]
    //    public EventCallback<TValue> ValueChanged { get; set; }

    //    [Parameter]
    //    public Expression<Func<TValue>> ValueExpression { get; set; }

    //    protected TValue? CurrentValue
    //    {
    //        get => Value;
    //        set
    //        {
    //            var hasChanged = !EqualityComparer<TValue>.Default.Equals(value, Value);
    //            if (hasChanged)
    //            {
    //                Value = value;
    //                ValueChanged.InvokeAsync(Value);
    //                editContext?.NotifyFieldChanged(fieldIdentifier);
    //            }
    //        }
    //    }

    //    protected string? CurrentValueAsString
    //    {
    //        get => FormatValueAsString(CurrentValue);
    //        set
    //        {
    //            messageStore?.Clear(fieldIdentifier);

    //            bool parsingFailed;

    //            if (_nullableUnderlyingType != null && string.IsNullOrEmpty(value))
    //            {
    //                // Assume if it's a nullable type, null/empty inputs should correspond to default(T)
    //                // Then all subclasses get nullable support almost automatically (they just have to
    //                // not reject Nullable<T> based on the type itself).
    //                parsingFailed = false;
    //                CurrentValue = default!;
    //            }
    //            else if (TryParseValueFromString(value, out var parsedValue, out var validationErrorMessage))
    //            {
    //                parsingFailed = false;
    //                CurrentValue = parsedValue!;
    //            }
    //            else
    //            {
    //                parsingFailed = true;

    //                // EditContext may be null if the input is not a child component of EditForm.
    //                if (editContext is not null)
    //                {
    //                    messageStore ??= new ValidationMessageStore(editContext);
    //                    messageStore?.Add(fieldIdentifier, validationErrorMessage);

    //                    // Since we're not writing to CurrentValue, we'll need to notify about modification from here
    //                    editContext.NotifyFieldChanged(fieldIdentifier);
    //                }
    //            }

    //            // We can skip the validation notification if we were previously valid and still are
    //            if (parsingFailed || _previousParsingAttemptFailed)
    //            {
    //                editContext?.NotifyValidationStateChanged();
    //                _previousParsingAttemptFailed = parsingFailed;
    //            }
    //        }
    //    }

    //    protected virtual string? FormatValueAsString(TValue? value)
    //    => value?.ToString();

    //    protected abstract bool TryParseValueFromString(string value, [MaybeNullWhen(false)] out TValue result, [NotNullWhen(false)] out string? validationErrorMessage);


    //    [Parameter]
    //    public string? Id { get; set; }

    //    protected string? UniqueID { get; private set; }

    //    [Parameter]
    //    public bool Required { get; set; } = false;

    //    [Parameter]
    //    public string? RegEx { get; set; }

    //    protected bool? IsValid { get; set; }

    //    [Parameter]
    //    public string? ResourceId { get; set; }

    //    private bool _canEditResourceText;
    //    [Parameter]
    //    public bool CanEditResourceText
    //    {
    //        get => !string.IsNullOrEmpty(ResourceId) && _canEditResourceText;
    //        set => _canEditResourceText = value;
    //    }

    //    [Parameter]
    //    public string? Label { get; set; }

    //    private bool _showLabel = true;
    //    [Parameter]
    //    public bool ShowLabel
    //    {
    //        get => string.IsNullOrEmpty(Label) ? false : _showLabel;
    //        set => _showLabel = value;
    //    }

    //    [Parameter]
    //    public bool IsDisabled { get; set; }

    //    private bool _hidden = false;
    //    [Parameter]
    //    public bool Hidden
    //    {
    //        get => _hidden;
    //        set
    //        {
    //            _hidden = value;

    //            if (_hidden)
    //                AddClass("hidden");
    //            else
    //                RemoveClass("hidden");

    //            assignCssClasses();
    //        }
    //    }

    //    [Parameter]
    //    public string CssClassContainer { get; set; } = "";

    //    private string _cssClasses { get; set; } = string.Empty;

    //    private List<string>? CssClassList { get; set; } = new List<string>();

    //    [Parameter]
    //    public string CssClass
    //    {
    //        get => _cssClasses;
    //        set
    //        {
    //            value.Split(' ').ToList().ForEach(c => AddClass(c));
    //            assignCssClasses();
    //        }
    //    }

    //    public void AddClass(string className)
    //    {
    //        if (!CssClassList.Contains(className))
    //        {
    //            CssClassList.Add(className);
    //            assignCssClasses();
    //        }
    //    }

    //    public void RemoveClass(string className)
    //    {
    //        if (CssClassList.Contains(className))
    //        {
    //            CssClassList.Remove(className);
    //            assignCssClasses();
    //        }
    //    }

    //    protected string cssErrorClass { get; set; }

    //    protected override void OnInitialized()
    //    {
    //        base.OnInitialized();

    //        if (editContext != null)
    //        {
    //            messageStore = new ValidationMessageStore(editContext);

    //            editContext.OnValidationStateChanged += OnValidateStateChanged;
    //        }
    //    }

    //    public override Task SetParametersAsync(ParameterView parameters)
    //    {
    //        parameters.SetParameterProperties(this);

    //        if (!_hasInitializedParameters)
    //        {
    //            UniqueID = Convert.ToBase64String(Guid.NewGuid().ToByteArray()).Substring(0, 10).Replace("+", "-").Replace("/", "-");

    //            if (ValueExpression == null)
    //            {
    //                throw new InvalidOperationException($"{GetType()} requires a value for the 'ValueExpression' " +
    //                    $"parameter. Normally this is provided automatically when using 'bind-Value'.");
    //            }

    //            fieldIdentifier = FieldIdentifier.Create(ValueExpression);

    //            var requiredAttribute = fieldIdentifier.Model.GetAttributeFrom<System.ComponentModel.DataAnnotations.RequiredAttribute>(fieldIdentifier.FieldName);

    //            if (requiredAttribute != null)
    //                this.Required = !requiredAttribute.AllowEmptyStrings;
                

    //            if (Hidden)
    //                AddClass("hidden");
    //            else
    //                RemoveClass("hidden");

    //            assignCssClasses();

    //            _nullableUnderlyingType = Nullable.GetUnderlyingType(typeof(TValue));
    //            _hasInitializedParameters = true;
    //        }

    //        UpdateAdditionalValidationAttributes();

    //        // For derived components, retain the usual lifecycle with OnInit/OnParametersSet/etc.
    //        return base.SetParametersAsync(ParameterView.Empty);
    //    }

    //    public void Dispose()
    //    {
    //        if (editContext != null)
    //        {
    //            editContext.OnValidationStateChanged -= OnValidateStateChanged;
    //        }
    //    }

    //    private void OnValidateStateChanged(object? sender, ValidationStateChangedEventArgs eventArgs)
    //    {
    //        updateValidation();
    //    }

    //    private void updateValidation()
    //    {
    //        messageStore?.Clear();

    //        if (!String.IsNullOrEmpty(fieldIdentifier.FieldName))
    //        {
    //            this.IsValid = !this.editContext.GetValidationMessages(this.fieldIdentifier).Any();

    //            if (Required && string.IsNullOrEmpty(CurrentValueAsString))
    //            {
    //                this.IsValid = false;
    //                messageStore?.Add(fieldIdentifier, $"Il campo {fieldIdentifier.FieldName} è obbligatorio");
    //            }

    //            if (!string.IsNullOrEmpty(RegEx) && System.Text.RegularExpressions.Regex.IsMatch(CurrentValueAsString, RegEx))
    //            {
    //                this.IsValid = false;
    //                messageStore?.Add(fieldIdentifier, $"Il campo {fieldIdentifier.FieldName} non rispetta l'espressione regolare {RegEx}");
    //            }

    //            if (this.IsValid.Value && string.IsNullOrEmpty(this.CurrentValueAsString))
    //                this.IsValid = null;

    //            if (this.IsValid == null)
    //                this.cssErrorClass = string.Empty;
    //            else
    //                this.cssErrorClass = this.IsValid.Value ? "has-success" : "has-error has-danger";
    //        }
    //    }

    //    private void UpdateAdditionalValidationAttributes()
    //    {
    //        if (editContext is null)
    //        {
    //            return;
    //        }

    //        var hasAriaInvalidAttribute = AdditionalAttributes != null && AdditionalAttributes.ContainsKey("aria-invalid");
    //        if (editContext.GetValidationMessages(fieldIdentifier).Any())
    //        {
    //            if (hasAriaInvalidAttribute)
    //            {
    //                // Do not overwrite the attribute value
    //                return;
    //            }

    //            if (ConvertToDictionary(AdditionalAttributes, out var additionalAttributes))
    //            {
    //                AdditionalAttributes = additionalAttributes;
    //            }

    //            // To make the `Input` components accessible by default
    //            // we will automatically render the `aria-invalid` attribute when the validation fails
    //            // value must be "true" see https://www.w3.org/TR/wai-aria-1.1/#aria-invalid
    //            additionalAttributes["aria-invalid"] = "true";
    //        }
    //        else if (hasAriaInvalidAttribute)
    //        {
    //            // No validation errors. Need to remove `aria-invalid` if it was rendered already

    //            if (AdditionalAttributes!.Count == 1)
    //            {
    //                // Only aria-invalid argument is present which we don't need any more
    //                AdditionalAttributes = null;
    //            }
    //            else
    //            {
    //                if (ConvertToDictionary(AdditionalAttributes, out var additionalAttributes))
    //                {
    //                    AdditionalAttributes = additionalAttributes;
    //                }

    //                additionalAttributes.Remove("aria-invalid");
    //            }
    //        }
    //    }

    //    /// <summary>
    //    /// Returns a dictionary with the same values as the specified <paramref name="source"/>.
    //    /// </summary>
    //    /// <returns>true, if a new dictionary with copied values was created. false - otherwise.</returns>
    //    private static bool ConvertToDictionary(IReadOnlyDictionary<string, object>? source, out Dictionary<string, object> result)
    //    {
    //        var newDictionaryCreated = true;
    //        if (source == null)
    //        {
    //            result = new Dictionary<string, object>();
    //        }
    //        else if (source is Dictionary<string, object> currentDictionary)
    //        {
    //            result = currentDictionary;
    //            newDictionaryCreated = false;
    //        }
    //        else
    //        {
    //            result = new Dictionary<string, object>();
    //            foreach (var item in source)
    //            {
    //                result.Add(item.Key, item.Value);
    //            }
    //        }

    //        return newDictionaryCreated;
    //    }


    //    private void assignCssClasses()
    //    {
    //        if (CssClassList?.Count == 1)
    //            _cssClasses = CssClassList[0];
    //        else if (CssClassList?.Count > 1)
    //            _cssClasses = string.Join(" ", CssClassList.ToArray());
    //        else if (CssClassList?.Count == 0)
    //            _cssClasses = string.Empty;
    //    }
    }
}

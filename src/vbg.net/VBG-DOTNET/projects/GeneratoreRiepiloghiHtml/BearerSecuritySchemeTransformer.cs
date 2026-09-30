using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.OpenApi;
using Microsoft.OpenApi;

internal sealed class BearerSecuritySchemeTransformer(IAuthenticationSchemeProvider authenticationSchemeProvider) : IOpenApiDocumentTransformer
{
    public async Task TransformAsync(OpenApiDocument document, OpenApiDocumentTransformerContext context, CancellationToken cancellationToken)
    {
        var authenticationSchemes = await authenticationSchemeProvider.GetAllSchemesAsync();
        if (authenticationSchemes.Any(authScheme => authScheme.Name == "Bearer"))
        {
            var bearerScheme = new OpenApiSecurityScheme 
            {
                Type = SecuritySchemeType.Http, 
                Scheme = "bearer", 
                In = ParameterLocation.Header, 
                BearerFormat = "Json Web Token" 
            };

            document.Components ??= new OpenApiComponents();

            document.Components.SecuritySchemes = new Dictionary<string, IOpenApiSecurityScheme> 
            {
                ["Bearer"] = bearerScheme 
            };

            var bearerReference = new OpenApiSecuritySchemeReference("bearer", document);

            foreach (var operation in document.Paths.Values.SelectMany(path => path.Operations)) 
            {
                operation.Value.Security?.Add(new OpenApiSecurityRequirement
                {
                    [bearerReference] = new List<string>()
                }); 
            }
        }
    }
}
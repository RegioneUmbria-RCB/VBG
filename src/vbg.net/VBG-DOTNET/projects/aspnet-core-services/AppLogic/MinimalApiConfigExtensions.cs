using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager.Manager;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Options;
using Newtonsoft.Json.Linq;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices;
using SIGePro.Data.Data.Rating;
using SIGePro.Data.Data.Rating.Utils;
using Vbg.EventBus;
using static AspnetCoreServices.AppLogic.BackendServicesConfigurationExtensions;

namespace AspnetCoreServices.AppLogic
{
    public static class MinimalApiConfigExtensions
    {
        public static void RegistraRatingMinimalApi(this WebApplication app)
        {
            // API per richiedere uno o più tipi di rating relativi ad un comune (ricavato dal token)
            app.MapGet(Constants.API_GET_RATING_ENDPOINT + "/{token}/{tipo?}", (
                string token,
                string? tipo,
                ITokenValidatorService tokenValidator,
                ILoggerFactory loggerFactory) =>
                {
                    AuthenticationInfo ai;

                    try
                    {
                        ai = tokenValidator.CheckToken(token);
                    }
                    catch (InvalidTokenException)
                    {
                        loggerFactory.CreateLogger("RatingApi").LogWarning($"Token non valido: {token}");
                        return Results.Unauthorized();
                    }

                    using var db = ai.CreateDatabase();
                    //loggerFactory.CreateLogger("RatingApi").LogWarning($"conn db: {db.ConnectionDetails.ConnectionString}");

                    var mgr = new RatingMgr(db);

                    IEnumerable<RatingMain> mains;

                    if (string.IsNullOrEmpty(tipo))
                        mains = mgr.GetMainList(ai.IdComune);
                    else
                        mains = new List<RatingMain> { mgr.GetMainListByType(ai.IdComune, tipo) }
                                .Where(x => x != null)
                                .ToList();

                    if (!mains.Any())
                        return Results.NotFound($"Nessun Rating trovato nel comune {ai.IdComune}");

                    var mainIds = mains.Select(m => m.Id).ToList();
                    var subSections = mainIds.SelectMany(mid => mgr.GetSubSectionsByMainId(ai.IdComune, mid.Value)).ToList();
                    var subIds = subSections.Select(s => s.Id).ToList();
                    var answers = subIds.SelectMany(sid => mgr.GetChoicesBySubSectionId(ai.IdComune, sid.Value)).ToList();

                    var dto = RatingDtoExtensions.ToDto(mains, subSections, answers);

                    return Results.Ok(dto);
                });

            // API per inviare i dati di un rating
            app.MapPost(Constants.API_POST_RATING_RESULT_ENDPOINT, async (
                HttpRequest request, 
                ITokenValidatorService tokenValidator, 
                ILoggerFactory loggerFactory) =>
            {
                var token = request.Headers["Authorization"].ToString()?.Replace("Bearer ", "");
                if (string.IsNullOrWhiteSpace(token))
                {
                    return Results.BadRequest("Token non presente nell'header Authorization");
                }

                AuthenticationInfo ai;

                try
                {
                    ai = tokenValidator.CheckToken(token);
                }
                catch (InvalidTokenException)
                {
                    loggerFactory.CreateLogger("RatingApi").LogWarning($"Token non valido: {token}");
                    return Results.Unauthorized();
                }

                var form = await request.ReadFormAsync();

                int ratingPositive = int.Parse(form["ratingPositive"]);
                int ratingNegative = int.Parse(form["ratingNegative"]);

                var rating = new RatingResult()
                {
                    IdComune = ai.IdComune,
                    Stars = int.TryParse(form["ratingA"], out var stars) ? stars : 0,
                    Identifier = form["identifier"],
                    FkIdChoice = Math.Max(ratingPositive, ratingNegative),
                    RatingComment = form["ratingComment"]
                };

                if (rating.Stars == 0 || string.IsNullOrEmpty(rating.IdComune) || string.IsNullOrEmpty(rating.Identifier) || rating.FkIdChoice == 0)
                {
                    return Results.BadRequest("Dati di rating incompleti o non validi");
                }

                using (var db = ai.CreateDatabase())
                {
                    var mgr = new RatingMgr(db);
                    mgr.SaveRatingResult(rating);
                }

                return Results.Ok();

            });

        }
    }
}

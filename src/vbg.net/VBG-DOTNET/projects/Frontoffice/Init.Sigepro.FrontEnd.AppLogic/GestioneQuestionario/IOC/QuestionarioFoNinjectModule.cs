using VBG.Shared.Infrastructure.DependencyInjection;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneQuestionario.IOC
{
    public static class QuestionarioFoNinjectModule
    {
        public static IDIProvider ConfiguraQuestionarioFo(this IDIProvider k)
        {
            k.AddScoped<IQuestionarioSoddisfazioneDao, QuestionarioWebServiceDao>();
            k.AddScoped<IQuestionarioSoddisfazioneService, QuestionarioSoddisfazioneService>();

            return k;
        }
    }
}

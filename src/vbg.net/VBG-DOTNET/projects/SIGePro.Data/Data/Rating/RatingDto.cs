using SIGePro.Data.Data.Rating.Utils;
using System.Collections.Generic;
using System.Linq;

namespace SIGePro.Data.Data.Rating
{
    public class RatingDto
    {
        public List<RatingMainDto> Ratings { get; set; }
    }

    public class RatingMainDto
    {
        public string TypeRating { get; set; }
        public string Title { get; set; }
        public RatingSubSectionDto NegativeRating { get; set; }
        public RatingSubSectionDto PositiveRating { get; set; }
    }

    public class RatingSubSectionDto
    {
        public string Question { get; set; }
        public List<RatingAnswerDto> Answers { get; set; } = new List<RatingAnswerDto>();
        public string Details { get; set; } = "Vuoi aggiungere altri dettagli?";
        public string CloseText { get; set; } = "Grazie, il tuo parere ci aiuterà a migliorare il servizio!";
        public string ActionLink { get; set; } = "{0}" + Constants.API_POST_RATING_RESULT_ENDPOINT;
    }

    public class RatingAnswerDto
    {
        public decimal Value { get; set; }
        public string Title { get; set; }
        public int Order { get; set; }
    }

    public static class RatingDtoExtensions
    {
        public static RatingDto ToDto(
            IEnumerable<RatingMain> mains,
            IEnumerable<RatingSubSection> subSections,
            IEnumerable<RatingAnswer> answers)
        {
            var subGroups = subSections.GroupBy(s => s.IdRatingMain)
                                       .ToDictionary(g => g.Key, g => g.ToList());

            var answersGroups = answers.GroupBy(a => a.IdRatingSubQuestion)
                                       .ToDictionary(g => g.Key, g => g.ToList());

            return new RatingDto
            {
                Ratings = mains.Select(main =>
                {
                    subGroups.TryGetValue(main.Id.Value, out var subs);

                    var positive = subs?.FirstOrDefault(s => s.IsPositive == 1);
                    var negative = subs?.FirstOrDefault(s => s.IsPositive == 0);

                    return new RatingMainDto
                    {
                        TypeRating = main.Tipo,
                        Title = main.Testo,
                        PositiveRating = positive == null ? null : new RatingSubSectionDto
                        {
                            Question = positive.Testo,
                            Answers = answersGroups.TryGetValue(positive.Id.Value, out var ansPos)
                                      ? ansPos.Select(a => new RatingAnswerDto
                                      {
                                          Value = a.Id.Value,
                                          Title = a.Testo,
                                          Order = a.Ordine
                                      }).ToList()
                                      : new List<RatingAnswerDto>()
                        },
                        NegativeRating = negative == null ? null : new RatingSubSectionDto
                        {
                            Question = negative.Testo,
                            Answers = answersGroups.TryGetValue(negative.Id.Value, out var ansNeg)
                                      ? ansNeg.Select(a => new RatingAnswerDto
                                      {
                                          Value = a.Id.Value,
                                          Title = a.Testo,
                                          Order = a.Ordine
                                      }).ToList()
                                      : new List<RatingAnswerDto>()
                        }
                    };
                }).ToList()
            };
        }


    }
}

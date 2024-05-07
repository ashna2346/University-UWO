# Computer Science 1026A - Section 001
# Ashna Mittal - 251206758

from string import punctuation
# imports punctuation variable


def compute_tweets(tweets_txt, keywords_txt):

    try:
        keyword_dict = {}

        pacific_total = 0
        mountain_total = 0
        central_total = 0
        eastern_total = 0

        pacific_hpp_score = 0
        mountain_hpp_score = 0
        central_hpp_score = 0
        eastern_hpp_score = 0

        pacific_score = 0
        mountain_score = 0
        central_score = 0
        eastern_score = 0
        # variable initialization

        def happiness_score(words):
            # function to calculate happiness score
            hpp_score = 0
            count = 0
            for word in words:
                word = word.strip(punctuation)
                if word.lower() in keyword_dict:
                    hpp_score += int(keyword_dict[word.lower()])
                    count += 1
            tweet_hpp_score = hpp_score / count
            return tweet_hpp_score

        with open(tweets_txt, "r", encoding="utf-8") as tweets:
            tweets_list = tweets.readlines()
            # reading the values in the textfile
            with open(keywords_txt, "r", encoding="utf-8") as keywords:
                keywords_list = keywords.readlines()
                # reading the values in the textfile
                for pair in keywords_list:
                    key, val = pair.split(',')
                    # splitting the words and numbers
                    keyword_dict[key] = val

                for tweet in tweets_list:
                    words_list = tweet.split(' ')
                    end = int(tweet.find(']'))
                    coords = tweet[1:end]
                    # storing the coordinates
                    lat, long = coords.split(', ')

                    if 24.660845 <= float(lat) <= 49.189787:
                        if -125.242264 <= float(long) < -115.236428:
                            pacific_total += 1
                            try:
                                pacific_hpp_score += happiness_score(words_list)
                                if happiness_score(words_list) > 0:
                                    pacific_score += 1
                            except:
                                pacific_hpp_score += 0
                                # calculation of pacific region score

                        if -101.998892 <= float(long) < -87.518395:
                            central_total += 1
                            try:
                                central_hpp_score += happiness_score(words_list)
                                if happiness_score(words_list) > 0:
                                    central_score += 1
                            except:
                                central_hpp_score += 0
                                # calculation of central region score

                        if -115.236428 <= float(long) < -101.998892:
                            mountain_total += 1
                            try:
                                mountain_hpp_score += happiness_score(words_list)
                                if happiness_score(words_list) > 0:
                                    mountain_score += 1
                            except:
                                mountain_hpp_score += 0
                                # calculation of mountain region score

                        if -87.518395 <= float(long) <= -67.444574:
                            eastern_total += 1
                            try:
                                eastern_hpp_score += happiness_score(words_list)
                                if happiness_score(words_list) > 0:
                                    eastern_score += 1
                            except:
                                eastern_hpp_score += 0
                                # calculation of eastern region score

        if pacific_score and pacific_hpp_score != 0:
            pacific_avg = pacific_hpp_score / pacific_score
        else:
            pacific_avg = 0

        if mountain_score and mountain_hpp_score != 0:
            mountain_avg = mountain_hpp_score / mountain_score
        else:
            mountain_avg = 0

        if central_score and central_hpp_score != 0:
            central_avg = central_hpp_score / central_score
        else:
            central_avg = 0

        if eastern_score and eastern_hpp_score != 0:
            eastern_avg = eastern_hpp_score / eastern_score
        else:
            eastern_avg = 0
        # calculation of average

        eastern = (eastern_avg, eastern_score, eastern_total)
        central = (central_avg, central_score, central_total)
        mountain = (mountain_avg, mountain_score, mountain_total)
        pacific = (pacific_avg, pacific_score, pacific_total)
        return [eastern, central, mountain, pacific]
    # returning the final values stored in a tuple

    except:
        return []

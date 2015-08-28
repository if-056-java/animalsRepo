/**
 * Created by oleg on 11.08.2015.
 */
var adoptionModule = angular.module('AdoptionModule',['AnimalAdoptionValues', 'pascalprecht.translate']);

adoptionModule.config(function($translateProvider) {
    $translateProvider.useSanitizeValueStrategy('escapeParameters');

    $translateProvider.useStaticFilesLoader({
        prefix: 'lang-',
        suffix: '.json'
    });
    //$translateProvider.preferredLanguage('en');

    /**
     * This method define a user locale language
     */
    $translateProvider.determinePreferredLanguage(function () {
        /**
         * @const Set default value of language
         */
        DEFAULT_VALUE = 'en';

        /**
         * @const Get a browser locale language
         */
        PREFERRED_LANGUAGE = navigator.language || navigator.userLanguage ||
                                           navigator.browserLanguage || navigator.systemLanguage || DEFAULT_VALUE;

        //check if we have user locale
        if(PREFERRED_LANGUAGE !== 'en' && PREFERRED_LANGUAGE !== 'uk')
            return DEFAULT_VALUE;

        return PREFERRED_LANGUAGE;
    });

    //$translateProvider.useLocalStorage();
});

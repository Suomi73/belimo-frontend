module.exports = {
    'Successfull open Portal App': function (browser) {


        browser
            .url(browser.launchUrl)

            .waitForElementVisible('div[class=col-md-offset-1] h1', 2000)
            .assert.containsText('div[class=col-md-offset-1] h1', 'Welcome to User App!')
            .assert.visible(".table")
            .assert.visible("#usersListLink")
            .assert.visible('#addUserLink')

            .end();
    },
    //todo: Testfall 2 für die Sprache: Französisch
};


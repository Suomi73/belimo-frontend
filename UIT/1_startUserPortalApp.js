module.exports = {
  'Successfull open Portal App': function (browser) {


    browser
      .url(browser.launchUrl)

      .waitForElementVisible('div[class=col-md-offset-1] h1', 2000)
      .assert.containsText('div[class=col-md-offset-1] h1', 'Welcome to User App!')
      .assert.visible("#usersListLink")
      .assert.visible('#addUserLink')

      .waitForElementVisible('#usersListLink', 2000)
      .click('#usersListLink')
      .waitForElementVisible('.table', 2000)

      .end();
  },
  //todo: Testfall 2 für die Sprache: Französisch
};


module.exports = {
  'Funktionsaufruf: deleteUser Successfull': function (browser) {


    browser
      .url(browser.launchUrl)

      .waitForElementVisible('div[class=col-md-offset-1] h1', 2000)
      .assert.containsText('div[class=col-md-offset-1] h1', 'Welcome to User App!')

      .waitForElementVisible('#usersListLink', 2000)
      .click('#usersListLink')
      .assert.visible('button')

      .click('button')

      .end();
  },
};

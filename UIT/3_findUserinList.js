module.exports = {
  'Funktionsaufruf: findUserinList Successfull': function (browser) {

    browser
      .url(browser.launchUrl)

      .waitForElementVisible('div[class=col-md-offset-1] h1', 2000)
      .assert.containsText('div[class=col-md-offset-1] h1', 'Welcome to User App!')

      .waitForElementVisible('#usersListLink', 2000)
      .click('#usersListLink')
      .assert.visible('table tbody tr:nth-of-type(1) td:nth-of-type(2)')
      .assert.containsText('table tbody tr:nth-of-type(1) td:nth-of-type(2)', 'F123456789')

      .end();
  },
};

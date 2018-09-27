module.exports = {
  'Funktionsaufruf: addNewUser Successfull': function (browser) {


    browser
      .url(browser.launchUrl)

      .waitForElementVisible('div[class=col-md-offset-1] h1', 2000)
      .assert.containsText('div[class=col-md-offset-1] h1', 'Welcome to User App!')

      .waitForElementVisible('#addUserLink', 2000)
      .click('#addUserLink')

      .waitForElementVisible('#email', 2000)
      .setValue('#email', 'testUser@adesso.ch')
      .assert.visible('#firstName')
      .setValue('#firstName', 'F123456789')
      .assert.visible('#lastName')
      .setValue('#lastName', 'L123456789')
      .assert.visible('.btn-success')
      .click('.btn-success')
      .pause(250)

      .end();
  },
};

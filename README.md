# Selenium-poc
SPIKE to check the feasibility of using Selenium to generate PDF in headless chrome mode

Testpdf.java: Generates PDF in the local environment/system
DockerSeleniumTest.java: Generated PDF using Selenium-Docker

# Setup
To generate PDF locally: 
Download Chromedriver according to the Chrome browser : https://chromedriver.chromium.org/downloads
Set property: webdriver.chrome.driver to the installed location. For reference check Testpdf.class.

To generate PDF with selenium-docker:
docker pull selenium/standalone-chrome
docker run -d -p 4445:4444 -v /dev/shm:/dev/shm selenium/standalone-chrome

To generate PDF with selenium-grid (handles multiple concurrent requests):
use following docker compose file: docker-grid-compose.yml
run this docker-compose file: docker-compose -f docker-grid-compose.yml up
This will bring up three chrome nodes and registers with selenium hub in docker. That means it can handle 3 requests in parallel.

Confluence Link: https://confluence.logrhythm.com/display/NGP/%5BSpike%5D+Headless+Chrome+And+Libraries+for+PDF+Report+Generation


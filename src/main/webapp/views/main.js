/*
 * Cip&Ciop
 * Copyright (C) 2012 Stefano Fornari
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License version 3 as published by
 * the Free Software Foundation with the addition of the following permission
 * added to Section 15 as permitted in Section 7(a): FOR ANY PART OF THE COVERED
 * WORK IN WHICH THE COPYRIGHT IS OWNED BY Stefano Fornari, Stefano Fornari
 * DISCLAIMS THE WARRANTY OF NON INFRINGEMENT OF THIRD PARTY RIGHTS.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see http://www.gnu.org/licenses or write to
 * the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301 USA.
 */

var ciops    = [],    // loaded tweets
    ccMap    = {},    // map of cc_id -> tweet
    loading  = false; // are we loading now

// custom background for a cip
function bubbleBg () {
    var prefix = "bubble-"; 
    return new uki.background.Sliced9({ 
        c: [prefix + "c.png", "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAABj0lEQVQ4jZ2U666CQAyEF1g14A+jvv+jGZ9AiTEG78pXnU1FkwNnkrrr0hm6pW32aBFavBfD/X4P/kxrlmUfa57niaOz2CUiplV7L4ghhHHOyhl71iiCF7ndbmZeVIISK4rCzIumCHXN6/VqdrlcwmQyCdPp9ONa8muaJhyPxzAajUKM0Qxxe2kbyUNi5/M5nE6nsFgsvoS6gLPdbu3F4/HYRC16XRVBxObz+Z9i+iD4woGr1CRBouMKCr0P8IUD90uQvJAz/5H6GBy4PyMcEp2P0kcYVRLkwRf3EMAFJuirf7PZ/EvQa+TaYGq5IYAjPsj1hzra7XbpYV+DAze1JT8klgLd7/cpH32ALxy4aLxb89WHVVWF2WwW1ut1L1F88IUDV30eFSHtQ8vhuFqtrAv4z3W6QrRcXddhuVyaD1xFmLVJtZHIdMGZIoWAHQ4HGxR+2tAZFDNCWFmWaTiY4OOFVJgIU6gI06caYfYF36OLnCHkI1PqkiDwc9HvPRLRi7g6jNoI3sFP61/Puyt4Am0NjZ06AwKpAAAAAElFTkSuQmCC"],
        v: [prefix + "v.png", "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAABDCAYAAACcAiVCAAAAeklEQVRYw+2WsQrAIAxEa///C/MZmTKI19WCS4NCxXdwkxhyvhu8I0LuLjNTrTVlM5O7KyJ0X50kpdzrNXCG1gzMRh1FXxv5ICj7RAYKUBIbttZS3rg2FJvIQAEKGwIFKEABChseXptSSsp82o9+w2mRRwdf1N//f+QHOSTzcjXgSDUAAAAASUVORK5CYII=", false, true],
        h: [prefix + "h.png", "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEcAAAAUCAYAAADfqiBGAAAAd0lEQVQYGe3BQQ2AQBADwDYpBnihAP9KcIIA7lOyue+ug85orWVES2stFNuIjSSKvu9DsY3YSKLoPE9ET7YRPSFGQoxkG9ETYiTbiJ7e90X0hBiJJKInxEiIkRAjPc+D6Om+b0RP13Wh2EZsJFF0HAeKbcRGEuUHmnolQf2UKf0AAAAASUVORK5CYII="],
        m: [prefix + "m.png", "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEcAAABDCAYAAADOIRgJAAAAiklEQVR42u3VwQkAIAwEQQP2X3FIbEDBrzgpYZnTmZk93PZmtzbHOBKIY1bkkEOOOGZFDjmOHHHMihxyyHkrTlWpQI444ojjKydHHLMix5FDjjhmRQ455JBDjjjOrMghhxxxzIoccshx4pgVOeSQQw454pjVt3EiQgWzEkccbw454pgVOeI4s7q4BXGJRsdjrAwPAAAAAElFTkSuQmCC", false, true]
    }, "10 10 10 10", {inset: '0 0 7 0'});
}

// widget layout
var widget = uki({ 
    view: 'Box', rect: '200 300', minSize: '200 300', visible: true, // widget parent view with white background
    anchors: 'left top right bottom', background: '#FFF',            // grow with the the container dom
    childViews: [
        { view: 'Box', rect: '200 51',                               // top panel, with default uki panel bg
            anchors: 'left top right', background: 'theme(panel)',   // width grows, height fixed to 51 
            style: { zIndex: 200 }, childViews: [
                { view: 'MultilineTextField', rect: '5 5 130 42',    // Cip input field
                    anchors: 'left top right', 
                    placeholder: "What's happening?", fontSize: '12px' },
                { view: 'Button', rect: '140 5 55 24', anchors: 'right top',  // Update button
                    text: 'Cip' }
            ] },
        { view: 'ScrollPane', rect: '0 50 200 250',                  // Scrollable tweet container
            anchors: 'left top right bottom', childViews: [
                { view: 'VFlow',  rect: '5 5 190 250',        // Flow of tweet views
                    anchors: 'left top right bottom', childViews: [
                        { view: 'Image', rect: '100 0 32 32', anchors: 'left top', src: 'loading.gif' }
                    ] }
            ] }
    ]
});

// Tweet contents template
var tweetTemplate = new uki.theme.Template(
    '<a href="http://twitter.com/${screen_name}">${screen_name}</a> ${text}'
);

// layout for a particular cip
function layoutCiop (ciop, flow) {
    var data = {
        screen_name: ciop.user.screen_name,
        text: uki.escapeHTML(ciop.text)
            .replace(/([\w]+:\/\/[a-z0-9$_.+()*,;\/?:@&~=-]+[a-z0-9\/])/ig, '<a href="$1">$1</a>')
            .replace(/(\@(\w+))/g, '<a href="http://twitter.com/$2">$1</a>')
    };
        
    var row = uki({ 
        view: 'Box', background: bubbleBg(),        // box container with a custom bg
        rect: '200 80', anchors: 'left top right',  // grow with container
        childViews: [
            { view: 'Image', rect: '10 10 50 50', anchors: 'left top',  // author profile image
                src: ciop.user.profile_image_url },
            { view: 'Label', rect: '65 10 120 40', anchors: 'left top right', // tweet text
                multiline: true, html: tweetTemplate.render(data), 
                fontSize: '11px', lineHeight: '13px', textSelectable: true }
        ]
    });
    
    // resize cip and children to container width
    row.rect( new uki.geometry.Rect(flow.rect().width, row.rect().height) );
    
    // resize height to tweet contents
    row.find('Label').resizeToContents('height'); // label first ...
    row.resizeToContents('height');               // ... then row to match label
    row.rect().height += 20;                      // ... add 20px space below
    return row[0];
}

// update ciop list when new ciops loaded
function updateCiops (data) {
    var flow = widget.find('VFlow'), // get the container
        i = 0, 
        firstRow = flow.childViews()[0],    // store current first rendered view
        firstCiop = ciops[0] || {id:-1},  // and current first tweet data
        ending = uki('> :last', flow);
        
    flow.removeChild(ending[0]);
    while (data[i] && !ccMap[data[i].id]) { // while new tweets
        ccMap[data[i].id] = data[i];
        flow.insertBefore(layoutCiop(data[i], flow), firstRow); // insert new tweet view
        tweets.unshift(data[i++]);                              // ... add tweet to loaded tweets
    }
    flow.appendChild(ending[0]);
    flow.resizeToContents('height'); // resize list to contents
    flow.parent().layout();          // update dom
}

// append ciops to the end of the list
function appendCiops (data) {
    var flow = widget.find('VFlow'), // get the container
        ending = uki('> :last', flow);

    loading = false;
    flow.removeChild(ending[0]);
    uki.each(data, function(i, tweet) {
        if (!ccMap[data[i].id]) {
            ccMap[data[i].id] = data[i];
            flow.appendChild(layoutTweet(tweet, flow));
            ciops.push(tweet);
        }
    });
    flow.appendChild(ending[0]);
    // if (data.length < 20) ending.visible(false)
    flow.resizeToContents('height'); // resize list to contents
    flow.parent().layout();          // update dom
}

// post a cip on cip button click
widget.find('Button[text=Cip]').click(function() {

    var form = uki.createElement('form', 'position:absolute;left:-999em', '<input type="text" name="cip" value="' + uki.escapeHTML(widget.find('MultilineTextField').value()) + '">');
    form.method = 'POST';
    form.action = 'cip.bsh';
    
    // append them to body
    document.body.appendChild(form);
    
    form.submit();
});

// simplest JSONP request implementation
function jsonp (url, callback) {
    var name = 'jsonp' +  +new Date,
        script = document.createElement('script'),
        head = document.getElementsByTagName('head')[0];
    name = 'jsonp1268984584605';
    window[name] = callback;
    script.src = url.replace(/=\?/, '=' + name);
    head.insertBefore(script, head.firstChild);
}

// when we scroll tweet list, load more tweets if less than 50px available
widget.find('ScrollPane').scroll(function() {
    if (this.contentsSize().height - this.scrollTop() - this.rect().height < 50) {
        if (!loading && tweets.length) {
            jsonp('http://api.twitter.com/1/statuses/home_timeline.json?callback=?&max_id=' + tweets[tweets.length - 1].id, appendTweets);
            loading = true;
        }
    }
}); 

/**
// update tweet list every 5 minutes
setTimeout(function() {
    if (loading) return; 
    jsonp('http://api.twitter.com/1/statuses/home_timeline.json?callback=?', updateTweets);
}, 5 * 60 * 1000);

// load first portion of tweets
jsonp('http://api.twitter.com/1/statuses/home_timeline.json?callback=?', appendTweets);
**/

// attach created widget to dom tree
widget.attachTo( document.getElementById('main'), '200 300' );
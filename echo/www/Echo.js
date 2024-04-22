var exec = require('cordova/exec');

module.exports = {
    updateGame(onSuccess) {
        exec(onSuccess, ()=>{}, 'Echo', 'updateGame', []);
    },
}
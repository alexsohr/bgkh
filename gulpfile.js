var es = require('event-stream');
var gulp = require('gulp'),
    del = require('del');

var concat = require('gulp-concat');
var connect = require('gulp-connect');
var templateCache = require('gulp-angular-templatecache');
var ngAnnotate = require('gulp-ng-annotate');
var uglify = require('gulp-uglify');
var fs = require('fs');
var _ = require('lodash');

var config = require('./gulp/config');

var scripts = require('./src/main/webapp/app.scripts.json');

var source = {
    js: {
        main: 'app/main.js',
        src: [
            // application config
            'src/main/webapp/app.config.js',

            // application bootstrap file
            'src/main/webapp/app/main.js',

            // main module
            'src/main/webapp/app/app.js',

            // module files
            'src/main/webapp/app/**/module.js',

            // other js files [controllers, services, etc.]
            'src/main/webapp/app/**/!(module)*.js'
        ],
        tpl: 'src/main/webapp/app/**/*.tpl.html'
    }
};

var destinations = {
    js: 'build'
};


gulp.task('build', function(){
    return es.merge(gulp.src(source.js.src) , getTemplateStream())
         .pipe(ngAnnotate())
         // .pipe(uglify())
        .pipe(concat('appApp.js'))
        .pipe(gulp.dest(destinations.js));
});

gulp.task('clean', function() {
    return del(['src/main/webapp/content/js/'], { dot: true });
});

gulp.task('js', function(){
    return es.merge(gulp.src(source.js.src) , getTemplateStream())
        .pipe(concat('appApp.js'))
        .pipe(gulp.dest(config.app + 'content/js/'));
});

gulp.task('watch', function(){
    gulp.watch(source.js.src, ['js']);
    gulp.watch(source.js.tpl, ['js']);
});

gulp.task('connect', function() {
    connect.server({
        port: 8080
    });
});

gulp.task('vendor', function(){
    _.forIn(scripts.chunks, function(chunkScripts, chunkName){
        var paths = [];
        chunkScripts.forEach(function(script){
            var scriptFileName = scripts.paths[script];

            if (!fs.existsSync(__dirname + '/' + scriptFileName)) {

                throw console.error('Required path doesn\'t exist: ' + __dirname + '/' + scriptFileName, script)
            }
            paths.push(scriptFileName);
        });
        gulp.src(paths)
            .pipe(concat(chunkName + '.js'))
            //.on('error', swallowError)
            .pipe(gulp.dest(config.app + 'content/js/'))
    });
    return;
});

gulp.task('prod', ['vendor', 'build']);
gulp.task('dev', ['clean', 'js', 'vendor', 'watch']);
gulp.task('default', ['dev']);

var swallowError = function(error){
    console.log(error.toString());
    this.emit('end')
};

var getTemplateStream = function () {
    return gulp.src(source.js.tpl)
        .pipe(templateCache({
            root: 'app/',
            module: 'app'
        }))
};

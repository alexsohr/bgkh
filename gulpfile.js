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


var handleErrors = require('./gulp/handle-errors'),
    runSequence = require('run-sequence'),
    htmlmin = require('gulp-htmlmin'),
    chmod = require('gulp-chmod'),
    serve = require('./gulp/serve'),
    util = require('./gulp/utils'),
    copy = require('./gulp/copy'),
    inject = require('./gulp/inject'),
    build = require('./gulp/build');

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
        tpl: 'src/main/webapp/app/**/*.tpl.html',
        html: 'src/main/webapp/app/**/*.html'
    }
};

var destinations = {
    js: 'build'
};


gulp.task('copy:swagger', copy.swagger);

gulp.task('assets:prod', ['sounds', 'static-data', 'statics', 'copy:html', 'copy:swagger'], build);


gulp.task('copy:html', function () {
    return gulp.src(config.app + 'app/**/*.html')
        .pipe(gulp.dest(config.dist + 'app'));
});

gulp.task('statics', function () {
    return gulp.src(config.app + 'content/**')
        .pipe(gulp.dest(config.dist + 'content/'));
});

gulp.task('static-data', function () {
    return gulp.src(config.app + 'data/**')
        .pipe(gulp.dest(config.dist + 'data/'));
});

gulp.task('sounds', function () {
    return gulp.src(config.app + 'sound/**')
        .pipe(gulp.dest(config.dist + 'sound/'));
});

gulp.task('change-perm', function () {
    return gulp.src(config.app + '/**')
        .pipe(chmod(775))
        .pipe(gulp.dest(config.dist));
});

gulp.task('build', ['clean'], function (cb) {
    // runSequence(['js', 'vendor'], 'combine-template-app', 'assets:prod', cb);
    runSequence(['js', 'vendor'], 'assets:prod', cb);
});

gulp.task('js', function(){
    return es.merge(gulp.src(source.js.src) , getTemplateStream())
    // return es.merge(gulp.src(source.js.src))
         .pipe(ngAnnotate())
         .pipe(uglify())
        .pipe(concat('appApp.js'))
        .pipe(gulp.dest(config.app + 'content/js'));
});


gulp.task('combine-template-app', function () {
    return es.merge(gulp.src(config.app + 'content/js/appApp.js'), getTemplateStream())
        .pipe(concat('appApp.js'))
        .pipe(gulp.dest(config.app + 'content/js'));
});

gulp.task('clean', function() {
    return del(['src/main/webapp/content/js/'], { dot: true });
});


gulp.task('watch', function(){
    gulp.watch(source.js.src, ['js']);
    gulp.watch(source.js.tpl, ['js']);
    gulp.watch(source.js.html, ['js']);
});

gulp.task('serve', ['dev'], serve);

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

gulp.task('prod', ['build']);
gulp.task('dev', ['clean', 'js', 'vendor', 'watch']);
gulp.task('default', ['dev']);

var swallowError = function(error){
    console.log(error.toString());
    this.emit('end')
};

var getTemplateStream = function () {
    return gulp.src(source.js.tpl)
        .pipe(htmlmin({collapseWhitespace: true}))
        .pipe(templateCache({
            root: 'app/',
            module: 'app',
            moduleSystem: 'IIFE'
        }));
};

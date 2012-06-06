$(function() {
  /*
  Backbone.sync = function(method, model) {
    alert(method + ": " + JSON.stringify(model));
    model.id = 1;
    //
  };
  */
  
  var Chapter = Backbone.Model.extend({
    defaults: function() {
      return {
        title: "chpater 1"
        
      };
    },
    
    initialize: function() {
      if (!this.get("title")) {
        this.set({"title": this.defaults().title});
      }
    },
    
    url: function() {
      if(this.isNew()) {
        return 'chapter/new';
      } else {
        return 'chapter/'+ this.id;
      }
      
    }
  });
  
  var ChapterList = Backbone.Collection.extend({
    model: Chapter,
    url: function () {
      return 'books/' + '111';
    },
    
    parse: function(response) {
      console.log(response);
      return response.results;
    }
  });
  
  var chapterList = new ChapterList;
  
  var ChapterView = Backbone.View.extend({
    tagName: "li",
    
    template: _.template($('#chapter-template').html()),
    
    events: {
      "click .item-name"       : "detailChapter",
      //"dbclick .item-drag-icon": "dragHandler",
      //"click .item-remove"     : "removeChapter"
    },
    
    initialize: function() {
      this.model.bind('change', this.render, this);
      this.model.bind('destroy', this.remove, this);
    },
    
    render: function() {
      this.$el.html(this.template(this.model.toJSON()));
      return this;
    },
    
    detailChapter: function(e) {
      console.log("detailChapter");
    }
  });
  
  var AppView = Backbone.View.extend({
    el : $("#content"),
    
    events: {
      "click #add-chapter-btn" : "chapterDialog",
      //"click #create-chapter-btn" : "createChapter"
    },
    
    initialize: function() {
      chapterList.bind('add', this.addChapter, this);
      chapterList.bind('all', this.addAll, this);
      //chapterList.fetch();
    },
    
    render: function() {
      console.log("render~~");
    },
    
    addAll: function() {
      chapterList.each(this.addChapter);
    },
    
    addChapter: function(chapter) {
      var view = new ChapterView({model: chapter});
      this.$("#chapter-list").append(view.render().el);
    },
    
    chapterDialog: function() {
      $("#chap-popover").dialog();
    },
    /*
    createChapter: function() {
      var view = new ChapterView({model: {title : 'hello', url : '1'}});
      this.$("#chapter-list").append(view.render().el);
    },
    */
    hello: function () {
      console.log("ggg");
    },
    
    hello2: function () {
      console.log("hhh");
    }
    
  });
  
  var App = new AppView;
  
  var ChapterPopView = Backbone.View.extend({
    el : $("body"),
    
    events : {
      "click #create-chapter-btn" : "createChapter"
    },
    
    createChapter: function () {
      var chapter = new Chapter({title : 'hello'});
      var view = new ChapterView({model: chapter});
      this.$("#chapter-list").append(view.render().el);
      chapterList.push(chapter);
      chapter.save(null,
      { success: function (model, response) {
           model.id = response.responseText.id;
           console.log(model.id);
        },
        error: function (model, response) {
          var respon =  jQuery.parseJSON(response.responseText);//TODO removed
           model.id = respon.id;
          console.log(model);
          console.log(response);
        }
      });
    }
  });
  
  var popView = new ChapterPopView(); 
  
});

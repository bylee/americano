$(function() {
  
  var Book = Backbone.Model.extend({
    defaults: function() {
      return {
        title: "book 1"
      };
    },
    
    initialize: function() {
      if (!this.get("title")) {
        this.set({"title": this.defaults().title});
      }
    },
    
    url: function () {
      if (this.isNew()) {
        return '/book/';
      } else {
        return '/book/'+ this.id;
      }
    },
    
    parse: function (response) {
      console.log(response);
      return response;
    }
    
  });
  
  var BookList = Backbone.Collection.extend({
    model: Book,
    
    url: function () {
      return '/user/1/books/';
    },
    
    parse: function (response) {
      console.log(response);
      return response;
    }
  });
  
  var bookList = new BookList;
  
  var BookView = Backbone.View.extend({
    tagName: "li",
    
    template: _.template($('#book-template').html()),
    
    events: {
      "click .item-name"       : "detailBook",
    },
    
    initialize: function() {
      this.model.bind('change', this.render, this);
      this.model.bind('destroy', this.remove, this);
    },
    
    render: function() {
      this.$el.html(this.template(this.model.toJSON()));
      return this;
    },
    
    detailBook: function(e) {
      console.log("detailBook");
      console.log(e);
      e.preventDefault();
    }
  });
  
  var AppView = Backbone.View.extend({
    el : $("#content"),
    
    events: {
      "click #add-book-btn" : "bookDialog"
    },
    
    initialize: function() {
      bookList.bind('add', this.addBook, this);
      bookList.bind('all', this.render, this);
      bookList.fetch({add:true});
    },
    
    render: function() {
      console.log('render~');
    },
    
    addBook: function(book) {
      var view = new BookView({model: book});
      this.$("#book-list").append(view.render().el);
    },
    
    bookDialog: function() {
      $("#book-popover").dialog();
    }
  });
  
  var App = new AppView;
  
  var BookPopView = Backbone.View.extend({
    el : $('body'),
  
  
    events : {
      "click #create-book-btn" : "createBook"
    },
    
    createBook: function () {
      var bookData  = {
          title : this.$('input:text[name=book-title]').val(),
          subtitle : this.$('input:text[name=book-subtitle]').val()
      };
      
      var book = new Book((bookData));
      book.save(null,
      { success: function (model, response) {
           console.log(model);
           console.log(response);
           //model.id = response.responseText.id;
           //console.log(model.id);
           bookList.push(response);
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
  
  var popView = new BookPopView();
});

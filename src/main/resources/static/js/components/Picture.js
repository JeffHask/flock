var btnFile = {
    position: 'relative',
    overflow: 'hidden',
    alignSelf: 'center',
    alignItems: 'center'
};

var btnFileInput = {
    position: 'absolute',
    top: '0',
    right: '0',
    bottom: '0',
    left: '0',
    minWidth: '100%',
    minHeight: '100%',
    fontSize: '100px',
    textAlign: 'right',
    filter: 'alpha(opacity=0)',
    opacity: '0',
    outline: 'none',
    background: 'white',
    cursor: 'inherit',
    display: 'block'
};

var pictureSizing = {
    maxHeight: '500px',
    width: 'auto',
    maxWidth: '500px'
};

var Picture = React.createClass({
    mixins: [Reflux.connect(PictureStore, 'picture')],
    getInitialState: function () {
        return {picture: undefined};
    },
    componentDidMount: function() {
        PictureActions.getPicture(this.props.getterUrl);
    },
    handleFile: function (e) {
        var reader = new FileReader();
        var file = e.target.files[0];
        var fileName = file.name;
        var sendingUrl = this.props.sendingUrl;

        reader.onload = function (upload) {
            var fileData = upload.target.result;
            PictureActions.savePicture(fileData, fileName, sendingUrl);
        };
        reader.readAsDataURL(file);
    },
    render: function () {
        var picture = <h2 className="text-center">Loading Picture <i className="fa fa-spin fa-refresh"/></h2>;
        if (this.state.picture != undefined) {
            picture = (
                <div className="row">
                    <img src={(this.state.picture != null ? this.state.picture : "")} style={pictureSizing} alt="NO PICTURE"/>
                </div>
            )
        }
        if (this.props.pictureEditable === true) {
            return (
                <div className="container-fluid">
                    {picture}
                    <div className="row btn btn-default btnFile" style={btnFile}>Edit Picture<input
                        style={btnFileInput} type="file"
                        multiple={false}
                        onChange={this.handleFile}/>
                    </div>
                </div>
            );
        } else {
            return (
                <div className="container-fluid">
                    {picture}
                </div>
            )
        }
    }
});
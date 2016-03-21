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

var Picture = React.createClass({
    mixins: [Reflux.connect(PictureStore, 'picture')],
    getInitialState: function () {
        return {picture: undefined};
    },
    handleFile: function (e) {
        var reader = new FileReader();
        var file = e.target.files[0];
        PictureActions.savePicture(file);
        // reader.onload = function (upload) {
        //     var fileData = upload.target.result;
        //     PictureActions.savePicture(fileData);
        // };
        // reader.readAsDataURL(file);
    },
    render: function () {
        if (this.state.picture !== undefined) {
            return (
                <div className="container-fluid">
                    <div className="row">
                        <img src={this.state.picture} alt="NO PICTURE"/>
                    </div>
                    <div className="row btn btn-default btnFile" style={btnFile}>Edit Picture<input
                        style={btnFileInput} type="file"
                        multiple={false}
                        onChange={this.handleFile}/>
                    </div>
                </div>
            );
        } else {
            return (
                <div className="btn btn-default btnFile" style={btnFile}>Upload Picture<input
                    style={btnFileInput} type="file"
                    multiple={false}
                    onChange={this.handleFile}/>
                </div>
            )
        }
    }
});